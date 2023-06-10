package pt.ulusofona.deisi.cm2223.a22103439_a22004278.ui

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.R
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.IMDBRepository
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.databinding.FragmentRegistarFilmeBinding
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.model.Avaliacao
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.model.Cinema
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.model.IMDB
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.model.IMDBFilme
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

private lateinit var photoFile: File

class RegistarFilmeFragment : Fragment() {

    private lateinit var model: IMDB
    private lateinit var binding: FragmentRegistarFilmeBinding
    private var selectedImages = mutableListOf<File>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_registar_filme, container, false)
        binding = FragmentRegistarFilmeBinding.bind(view)
        photosClickEvent()

/*        val adapterFilme = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            App.filmesIMDB.map { it.nome }
        )

        val adapterCinema = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            App.cinemas.map { it.cinema_name }
        )

        binding.nameEditText.setAdapter(adapterFilme)

        binding.cinemaEditText.setAdapter(adapterCinema)*/

        model = IMDBRepository.getInstance()

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

            var preenchido = true

            if (nome.isEmpty() || nome == ""){
                binding.nameEditText.error = "Este campo é obrigatório"
                preenchido = false
            }

          /*  val filmeSelecionado = App.getFilmeByName(nome)
            if (filmeSelecionado == null){
                binding.nameEditText.error = "Este filme não existe"
                preenchido = false
            }else {
                if (App.reviewExists(filmeSelecionado)) {
                    binding.nameEditText.error = "A avaliação deste filme já está registada"
                    preenchido = false
                }
            }*/

            if (cinema.isEmpty() || cinema == ""){
                binding.cinemaEditText.error = "Este campo é obrigatório"
                preenchido = false
            }

/*
            val cinemaSelecionado = App.getCinemaByName(cinema)
            if (cinemaSelecionado == null){
                binding.cinemaEditText.error = "Este cinema não existe"
                preenchido = false
            }
*/

            if (date == null || date.after(Date()) || date == Date()) {
                binding.datePicker.error = "A data está inválida"
                preenchido = false
            }

            if (preenchido) {

                showConfirmationDialog(
                    message = "Tem a certeza que quer registar o filme ${nome}?",
                    positiveButtonAction = {
                        // Lógica a ser executada se o usuário clicar em "Sim"
                        // Cria uma nova instância de Movie com os dados do formulário

                        CoroutineScope(Dispatchers.IO).launch {
                            model.getFilmeIMDB(nome){
                                if(it.isSuccess){
                                    it.onSuccess {
                                        val filme = IMDBFilme(
                                            idIMDB = it.idIMDB,
                                            nomeIMDB = it.nomeIMDB,
                                            generoIMDB = it.generoIMDB,
                                            sinopseIMDB = it.sinopseIMDB,
                                            dataLancamentoIMDB = it.dataLancamentoIMDB,
                                            avaliacaoIMDB = it.avaliacaoIMDB,
                                            linkIMDB = it.linkIMDB,
                                            imagemCartazIMDB = it.imagemCartazIMDB
                                        )


                                        val avaliacao = Avaliacao(
                                            UUID.randomUUID().toString(),
                                            binding.seekBar.progress + 1,
                                            date!!,
                                            binding.notesEditText.text.toString(),
                                            filme,
                                            Cinema(1,"Colombo"),
                                            // selectedImages, // Coloque aqui a lista de URIs para as fotos tiradas com a câmera, caso deseje implementar
                                        )

                                        model.inserirAvaliacao(filme,avaliacao){
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
