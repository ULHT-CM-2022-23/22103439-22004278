package pt.ulusofona.deisi.cm2223.a22103439_a22004278.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.R
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.Repository
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.databinding.FragmentRegistarFilmeBinding
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.model.Avaliacao
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.model.Operations
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

private lateinit var photoFile: File

class RegistarFilmeFragment : Fragment() {

    private lateinit var model: Operations
    private lateinit var binding: FragmentRegistarFilmeBinding
    private var selectedImages = mutableListOf<File>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_registar_filme, container, false)
        binding = FragmentRegistarFilmeBinding.bind(view)
        photosClickEvent()
        model = Repository.getInstance()
        val cinemas = mutableListOf<String>()
        CoroutineScope(Dispatchers.IO).launch {
            model.getAllCinemas {
                if(it.isSuccess){
                    it.onSuccess {
                        it.forEach {
                            cinemas.add(it.nome)
                        }
                    }
                }
            }
        }

/*        val adapterFilme = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            App.filmesIMDB.map { it.nome }
        )*/
        val adapterCinema = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            cinemas
        )

        //binding.nameEditText.setAdapter(adapterFilme)

        binding.cinemaEditText.setAdapter(adapterCinema)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        //listaDados = App

        // Configuração do botão de guardar
        binding.saveButton.setOnClickListener {

            val nome : String = binding.nameEditText.text.toString()
            val cinema : String = binding.cinemaEditText.text.toString()
            val date : Date? = getDateFromDatePicker(binding.datePicker.text.toString())
            val rating = binding.seekBar.progress + 1

            var preenchido = true

            if (nome.isEmpty() || nome == ""){
                binding.nameEditText.error = R.string.error_required_field.toString()
                preenchido = false
            }

            if (cinema.isEmpty() || cinema == ""){
                binding.cinemaEditText.error = R.string.error_required_field.toString()
                preenchido = false
            }

            if (date == null || date.after(Date()) || date == Date()) {
                binding.datePicker.error = R.string.error_invalid_date.toString()
                preenchido = false
            }

            if (preenchido) {

                showConfirmationDialog(
                    message = "Tem a certeza que quer registar o filme ${nome}?",
                    positiveButtonAction = {
                        CoroutineScope(Dispatchers.IO).launch {

                            model.getFilmeByName(nome){ itFilme ->
                                if(itFilme.isFailure){
                                    CoroutineScope(Dispatchers.Main).launch {
                                        binding.nameEditText.error = R.string.error_movie_doesnt_exist.toString()
                                    }
                                } else if(itFilme.isSuccess) {
                                    itFilme.onSuccess { filme ->
                                        model.getAvaliacaoByFilme(filme.id){ itAvaliacao ->
                                            if(itAvaliacao.isSuccess){
                                                itAvaliacao.onSuccess {
                                                    CoroutineScope(Dispatchers.Main).launch {
                                                        binding.nameEditText.error = R.string.error_movie_already_used.toString()
                                                    }
                                                }
                                            } else if(itAvaliacao.isFailure) {
                                                model.getCinemaByNome(cinema) { itCinema ->
                                                    if (itCinema.isFailure) {
                                                        CoroutineScope(Dispatchers.Main).launch {
                                                            binding.cinemaEditText.error = R.string.error_cinema_doesnt_exist.toString()
                                                        }
                                                    } else if (itCinema.isSuccess) {
                                                        itCinema.onSuccess { cinema ->
                                                            val avaliacao = Avaliacao(
                                                                UUID.randomUUID().toString(),
                                                                rating,
                                                                date!!,
                                                                binding.notesEditText.text.toString(),
                                                                filme,
                                                                cinema,
                                                                // selectedImages, // Coloque aqui a lista de URIs para as fotos tiradas com a câmera, caso deseje implementar
                                                            )
                                                            model.inserirAvaliacao(filme, avaliacao) {}
                                                            CoroutineScope(Dispatchers.Main).launch {
                                                                clearFields()
                                                                Toast.makeText(
                                                                    requireContext(),
                                                                    R.string.success_movie_registered,
                                                                    Toast.LENGTH_LONG
                                                                ).show()
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    },
                    negativeButtonAction = {}
                )
            }
        }

        // Configuração do listener para o campo de data
        binding.datePicker.setOnClickListener {
            // Obtém a data atual para definir como data inicial no DatePicker
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH) - 1

            calendar.add(Calendar.DAY_OF_MONTH, -1)
            val maxDate = calendar.timeInMillis
            val locale = Locale(Locale.getDefault().language, Locale.getDefault().country)

            // Cria o DatePickerDialog
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    // Atualiza o campo de texto com a data selecionada
                    binding.datePicker.setText(
                        getString(
                            R.string.date_format,
                            year,
                            (month + 1),
                            dayOfMonth
                        )
                    )
                },
                year,
                month,
                day
            )

            // Exibe o DatePickerDialog
            datePickerDialog.datePicker.maxDate = maxDate

            // Define o estilo do DatePickerDialog para o estilo padrão em português
            datePickerDialog.datePicker.apply {
                // Define o estilo do DatePickerDialog para o estilo padrão em português
                context.resources.configuration.setLocale(locale)
            }

            datePickerDialog.show()
        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            @SuppressLint("SetTextI18n")
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, b: Boolean) {
                binding.rating.text = (progress + 1).toString()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
    }

    // Função auxiliar para obter a data selecionada no DatePicker
    private fun getDateFromDatePicker(dateString: String): Date? {
        val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
        return try {
            dateFormat.parse(dateString)
        } catch (e: Exception) {
            null
        }
    }

    private fun clearFields() {
        binding.nameEditText.text.clear()
        binding.cinemaEditText.text.clear()
        binding.datePicker.text?.clear()
        binding.notesEditText.text?.clear()
        binding.seekBar.progress = 0
        selectedImages.clear()
        binding.textInserido.text = "0"
    }

    fun showConfirmationDialog(
        message: String,
        positiveButtonAction: () -> Unit,
        negativeButtonAction: () -> Unit
    ) {
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setMessage(message)
        alertDialogBuilder.setPositiveButton(R.string.yes) { _, _ ->
            positiveButtonAction.invoke()
        }
        alertDialogBuilder.setNegativeButton(R.string.no) { _, _ ->
            negativeButtonAction.invoke()
        }
        alertDialogBuilder.show()
    }


    private fun photosClickEvent() {
        binding.photosButton.setOnClickListener {
            val permissions = arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )

            if (arePermissionsGranted(permissions)) {
                launchCameraIntent()
            } else {
                requestPermissionLauncher.launch(permissions)
            }
        }
    }

    private fun arePermissionsGranted(permissions: Array<String>): Boolean {
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    private fun launchCameraIntent() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        photoFile = createImageFile()

        val photoUri = FileProvider.getUriForFile(
            requireContext(),
            "pt.ulusofona.deisi.cm2223.a22103439_a22004278.fileprovider",
            photoFile
        )

        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
        activityResultLauncher.launch(intent)
    }

    var requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        // Verifique se as permissões foram concedidas ou não
        val allPermissionsGranted = permissions.values.all { it }
        if (allPermissionsGranted) {
            // Permissões concedidas, execute a ação desejada
            launchCameraIntent()
        } else {
            // Permissões negadas. Lidar com isso adequadamente.
        }
    }


    var activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // Obtenha a URI da imagem capturada usando o caminho do arquivo
                val uri = Uri.fromFile(photoFile)

                // Use a URI para exibir a imagem capturada
                selectedImages.add(photoFile)
                binding.textInserido.text = selectedImages.size.toString()
            }
        }


    private fun createImageFile():File{
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG${timeStamp}_", ".jpg", storageDir)
    }

}
