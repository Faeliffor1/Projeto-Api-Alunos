const formulario = document.querySelector("form");

const Inome = document.querySelector(".nome")
const IPortugues = document.querySelector(".Portugues")
const IMatematica = document.querySelector(".Matematica")
const ICiencias = document.querySelector(".Ciencias")
const IGeografia = document.querySelector(".Geografia")
const IHistória = document.querySelector(".História")
const Ifrequencia = document.querySelector(".frequencia")

function alunos () {

    fetch("http://localhost:8080/alunos",
        {
            headers:{
                'Accept':'application/json',
                'Content-Type': 'application/json'
            },
            method: "POST",
            body: JSON.stringify({
                nome: Inome.value,
                portugues: IPortugues.value,
                matematica: IMatematica.value,
                ciencias: ICiencias.value,
                geografia: IGeografia.value,
                historia: IHistória.value,
                frequencia: Ifrequencia.value
            })
        })
        .then(res => res.json())
        .then(data => console.log("Resposta do servidor:", data))
        .catch(err => console.error("Erro:", err));

};

function limpar (){
    Inome.value="";
    IPortugues.value = "";
    IMatematica.value = "";
    ICiencias.value = "";
    IGeografia.value = "";
    IHistória.value = "";
    Ifrequencia.value = "";

};

formulario.addEventListener("submit", function (event){
    event.preventDefault();

    alunos();
    limpar();

});

// Médias por disciplina
fetch("http://localhost:8080/alunos/medias-disciplinas")
  .then(res => res.json())
  .then(medias => {
    const lista = document.getElementById("mediasDisciplinas");
    lista.innerHTML = "";
    for (const [disciplina, media] of Object.entries(medias)) {
      const li = document.createElement("li");
      li.textContent = `${disciplina}: ${media.toFixed(2)}`;
      lista.appendChild(li);
    }
  })
  .catch(err => console.error("Erro ao buscar médias das disciplinas:", err));


/// Média da turma
fetch("http://localhost:8080/alunos/media-turma")
  .then(res => {
    if (!res.ok) throw new Error("Erro HTTP " + res.status);
    return res.json();
  })
  .then(media => {
    if (typeof media === "number") {
      document.getElementById("mediaTurma").textContent = media.toFixed(2);
    }
  })
  .catch(err => console.error("Erro ao buscar média da turma:", err));


// Alunos acima da média
fetch("http://localhost:8080/alunos/acima-media")
  .then(res => res.json())
  .then(alunos => {
    if (Array.isArray(alunos)) {
      const tabela = document.getElementById("acimaMedia");
      tabela.innerHTML = "";
      alunos.forEach(a => {
        const media = (a.portugues + a.matematica + a.ciencias + a.geografia + a.historia) / 5;
        const tr = document.createElement("tr");
        tr.innerHTML = `<td>${a.nome}</td><td>${media.toFixed(2)}</td>`;
        tabela.appendChild(tr);
      });
    }
  });

// Alunos com frequência baixa
fetch("http://localhost:8080/alunos/frequencia-baixa")
  .then(res => res.json())
  .then(alunos => {
    if (Array.isArray(alunos)) {
      const tabela = document.getElementById("freqBaixa");
      tabela.innerHTML = "";
      alunos.forEach(a => {
        const tr = document.createElement("tr");
        tr.innerHTML = `<td>${a.nome}</td><td class="alerta">${a.frequencia}%</td>`;
        tabela.appendChild(tr);
      });
    }
  });
