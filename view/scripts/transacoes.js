let vendaEndpoint = "http://localhost:8080/transacao/transacoes";

function hideLoader() {
    document.getElementById("loading").style.display = "none";
}

function show(transacoes) {
    let tab = `<thead>
    <th scope="col">#</th>
    <th scope="col">VENDAS</th>
    <th scope="col">DESCRIÇÃO</th>
    <th scope="col">VALOR_TRANSACAO</th>
    <th scope="col">TIPO_TRANSACAO</th>
    <th scope="col">USUARIO</th>
    <th scope="col">CAIXA</th>
    <th scope="col">AÇÃO</th>
   </thead>`;

for (let transacao of transacoes) {
tab+= `
    <tr>
        <td scope="row">${transacao.id}</td>
        <td>${transacao.vendas.map(venda => venda.id).join(", ")}</td>
        <td>${transacao.descricao}</td>
        <td>${transacao.valorTransacao}</td>
        <td>${transacao.tipoTransacao}</td>
        <td>${transacao.user.nome}</td>
        <td>${transacao.caixa.id}</td>
        <td><button type="button" onclick="deleteTransacao(${transacao.id})" class="btn btn-danger">Delete</button></td>
    </tr> `;
}

document.getElementById("transacoes").innerHTML = tab;
}

async function getTransacoes() {
    let key = "Authorization";
    const response = await fetch(vendaEndpoint, {
        method: "GET",
        headers: new Headers({
            Authorization: localStorage.getItem(key),

        }),
    });

    if (response.status === 401 || response.status === 403) {
        localStorage.clear();
        window.top.location = "/view/login.html";
    }

    var data = await response.json();
    console.log(data);
    if (response) hideLoader();
    show(data);
}

function createTransacao() {
    tab = `<form>
                <div class="mb-3">
                    <label class="form-label">Caixa_Id</label>
                    <input type="number"  class="form-control" id="caixaInput" aria-describedby="form-control" autocomplete="off">
                </div>
                <div class="mb-3">
                    <label class="form-label">Descrição</label>
                    <input type="Text"  class="form-control" id="descricaoInput">
                </div>
                <div class="mb-3">
                    <label class="form-label">Vendas</label>
                    <input type="number"  class="form-control" id="vendasInput">
                </div>
                <button type="button" onclick="newTransacao(${1})" class="btn btn-primary me-2">Receber</button>
                <button type="button" onclick="newTransacao(${0})" class="btn btn-primary">Débito</button>
            </form>`;

    document.getElementById("transacoes").innerHTML = tab;
}

async function newTransacao(tipo){
    let caixa = document.getElementById("caixaInput").value;
    let descricao = document.getElementById("descricaoInput").value;
    let ids = document.getElementById("vendasInput").value;
    const vendas_id = ids.split('.').map(Number);
    let key = "Authorization";
    if(tipo == 1){
        vendaEndpoint = "http://localhost:8080/transacao/receber"
    }else{
        vendaEndpoint = "http://localhost:8080/transacao/debitar"
    }
    const response = await fetch(vendaEndpoint, {
        method: "POST",
        headers: new Headers({
            "Content-Type": "application/json; charset=utf8",
            Accept: "application/json",
            Authorization: localStorage.getItem(key),
        }),
        body: JSON.stringify({
            caixa: caixa,
            descricao: descricao,
            vendas_id: vendas_id
        }),
    });

    if (response.ok) {
        showToast("#okToast");
      } else {
        showToast("#errorToast");
      }
    
      window.setTimeout(function () {
        window.location = "/view/transacoes.html";
      }, 1000);
}

async function deleteTransacao(transacaoId) {
    let key = "Authorization";
    const response = await fetch("http://localhost:8080/transacao/" + transacaoId, {
        method: "DELETE",
        headers: new Headers({
            Authorization: localStorage.getItem(key),
        }),
    });
    window.location = "/view/transacoes.html";
}

function showToast(id) {
    var toastElList = [].slice.call(document.querySelectorAll(id));
    var toastList = toastElList.map(function (toastEl) {
      return new bootstrap.Toast(toastEl);
    });
    toastList.forEach((toast) => toast.show());
  }

document.addEventListener("DOMContentLoaded", function (event) {
    if (!localStorage.getItem("Authorization"))
    window.top.location = "/view/login.html";
});

getTransacoes();