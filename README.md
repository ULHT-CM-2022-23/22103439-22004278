# Projeto Android Nativo

Projeto de android nativo para a cadeira de Computação Móvel do ano letivo 2022/2023 da Licenciatura em Engenharia Informática da Universidade Lusófona.


## Autores

- Alexandre Garcia - a22004278
- Diogo Silva - a22103439
Autoavaliação (parte 1): 15
Autoavaliação (parte 2): 16

## Video
https://youtu.be/XdTyTcv7FKo

## Screenshots

**Dashboard**
!["Dashboard"](https://i.imgur.com/fkEHgP4.png)

**Pesquisa por voz**
!["Pesquisa por voz"](https://i.imgur.com/u5pJIpM.png)

**Lista de filmes**
!["Lista de filmes"](https://i.imgur.com/CfbIBiN.png)

**Mapa**
!["Mapa"](https://i.imgur.com/2MQ6Vns.png)

**Detalhes de filme**
!["Detalhes de filme"](https://i.imgur.com/d92VEe5.png)
!["Detalhes de filme"](https://i.imgur.com/RvS5Y1P.png)

**Registo**
!["Registo (erros)"](https://i.imgur.com/sYYi6oN.png)

!["Registo (nome do filme)"](https://i.imgur.com/I9cXMF7.png)

!["Registo (nome do cinema)"](https://i.imgur.com/VnUadu7.png)

!["Registo (data)"](https://i.imgur.com/QGmp5Ig.png)

!["Registo (confirmação)"](https://i.imgur.com/dLlH0vy.png)


## Funcionalidades (Parte 1)

| Critério                                           | Funcionalidades                                                                                                                                                                       |
|----------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Dashboard                                          | Ecrã que apresenta uma lista dos filmes avaliados pelo utilizador e o top filmes ordenados por avaliação                                                                 |
| Apresentação dos filmes -Lista                     | Ecrã que apresenta a lista de filmes avaliados. Ao clicar no nome do filme, redireciona para os detalhes do mesmo.                                                                                                                    |
| Apresentação dos filmes - Lista - Rotação          | Ao fazer a rotação horizontal do telemóvel neste ecrã, acrescenta as observações de cada filme.                                                                                       |
| Apresentação dos filmes - Mapa (imagem)            | Existe um ecrã dedicado ao Mapa, onde aparece uma imagem básica.                                                                                            |
| Detalhes do filme                 | Ecrã que apresenta os detalhes do filme e informações adicionais, incluindo a avaliação e fotografias inseridas pelo utilizador. |
| Pesquisa de filmes por voz                         | Ao clicar no botão com ícone de microfone localizado à direita da toolbar, é apresentado um dialog com uma contagem decrescente de 10 segundos.                     |
| Registo de avaliações                | Ecrã com formulário para registar uma avaliação de um filme já existente na aplicação, incluindo fotografias tiradas com a câmara do telemóvel.                                                                                     |
| Suporte multi-idioma                               | A aplicação tem suporte para as línguas de português, inglês e espanhol.                                                                                                |
| Navegabilidade                                     | A navegação entre ecrãs é feita através de um drawer   |


## Funcionalidades (Parte 2)

| Critério                                           | Funcionalidades                                                                                                                                                                       |
|----------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Dashboard                                          | Ecrã que apresenta métricas da média das avaliações registadas, o número total de avaliações e uma lista dos melhores 5 filmes avaliados.                                             |
| Apresentação dos filmes - Lista                    | Ecrã que apresenta a lista de filmes avaliados. Ao clicar no nome do filme, redireciona para os detalhes do mesmo.                                                                    |
| Apresentação dos filmes - Lista - Rotação          | Ao fazer a rotação horizontal do telemóvel neste ecrã, acrescenta o nome do cinema onde foi visto e as observações de cada filme avaliado.                                            |
| Apresentação dos filmes - Mapa                     | Um ecrã especifico para o mapa onde contém as posições dos cinemas onde foram vistos os filmes avaliados. Os marcadores estão divididos por cores consoante o grau de satisfação.     |
| Detalhes do filme                                  | Ecrã que apresenta os detalhes do filme e informações adicionais, incluindo a avaliação e fotografias inseridas pelo utilizador.                                                      |
| Pesquisa de filmes por voz - Funcionalidade Avançada                         | Ao clicar no botão com ícone de microfone localizado à direita da toolbar, é apresentado um popup e a função de reconhecimento de voz. Ao clicar em Pesquisar e caso o filme exista, é redirecionado para a página dos detalhes do filme, e caso contrário, mostra uma mensagem de erro.                     |
| Registo de avaliações                              | Ecrã com formulário para registar uma avaliação de um filme existente na API do OMDb e cinemas existentes na base de dados, incluindo fotografias tiradas com a câmara do telemóvel. Também é possível limpar as fotografias. |
| Suporte multi-idioma                               | A aplicação tem suporte para as línguas de português, inglês e espanhol.                                                                                                |
| Navegabilidade                                     | A navegação entre ecrãs é feita através de um drawer.   |
| Funcionamento Offline - Funcionalidade Avançada    | Quando não há ligação à internet, a aplicação corre, no entanto, o registo e a pesquisa da voz são bloqueados e o utilizador recebe um aviso.                                        |

GRAUS DOS MARCADORES DO MAPA:
1 e 2 - VERMELHO
3 e 4 - LARANJA
5 e 6 - AMARELO
7 e 8 - VERDE
9 e 10 - AZUL

## Classes de lógica de negócio
Classe **Avaliacao**:
- Atributos:
    * id: String
    * avaliacao: Int
    * dataVisualizacao: Date
    * observacao: String
    * filme: Filme
    * cinema: Cinema

Classe **AvaliacaoFoto**:
- Atributos:
    * idAvaliacao: String
    * uriFoto: String

Classe **Cinema**:
- Atributos:
    * id: Int
    * nome: String
    * latitude: Double
    * longitude: Double
    * morada: String
    * localidade: String

Classe **CinemaRating**:
- Atributos:
    * idCinema: Int
    * categoria: String,
    * score: Int

Classe **Filme**:
- Atributos:
    * id: String
    * nome: String
    * genero: String
    * sinopse: String
    * dataLancamento: Date
    * avaliacao: Int
    * link: String
    * imagemCartaz: String
    * idiomas: String

Classe **Operations** *(Abstrata)*:
- Funções:
    * getFilmeByName(nomeFilme: String, onFinished: (Result<Filme>) -> Unit)
    * clearAll(onFinished: () -> Unit)
    * getAvaliacao(id: String, onFinished: (Result<Avaliacao>) -> Unit)
    * getAllAvaliacoes(onFinished: (Result <List<Avaliacao>>) -> Unit)
    * getTop5Avaliacoes(asc: Boolean, onFinished: (Result <List<Avaliacao>>) -> Unit)
    * getMediaAvaliacoes(onFinished: (Result <Float>) -> Unit)
    * getCountAvaliacoes(onFinished: (Result <Int>) -> Unit)
    * getAvaliacaoByFilme(idFilme: String, onFinished: (Result <Avaliacao>) -> Unit)
    * inserirAvaliacao(filme: Filme, avaliacao: Avaliacao, onFinished: (Result<Filme>) -> Unit)
    * inserirFotosAvaliacao(idAvaliacao: String, fotos: List<File>, onFinished: () -> Unit)
    * getFotosAvaliacao(idAvaliacao: String, onFinished: (Result<List<File>>) -> Unit)
    * getCinemaJSON(onFinished: (Result <List<Cinema>>) -> Unit)
    * getAllCinemas(onFinished: (Result <List<Cinema>>) -> Unit)
    * getCinemaByNome(nomeCinema: String, onFinished: (Result<Cinema>) -> Unit)
    * getCinemaById(idCinema: Int, onFinished: (Result<Cinema>) -> Unit)
    * getCinemaRating(idCinema: Int, onFinished: (Result<List<CinemaRating>>) -> Unit)
    * inserirCinemas(cinemas: List<Cinema>, ratings: List<CinemaRating>, onFinished: () -> Unit)
    * clearAllCinemas(onFinished: () -> Unit)

## Idiomas gerados

Para este projeto, para além do Português, foram ainda utilizados os idiomas de Inglês e Espanhol.

!["Prompt de tradução para espanhol"](https://i.imgur.com/NXGrAmg.png)
!["Prompt de tradução para inglês"](https://i.imgur.com/ZKeh5MD.png)

## Fonte de informação

- ChatGPT
- StackOverlow
- GitHub CoPilot
- Android Developers
