const clienteEndpoint = "http://localhost:8080/cliente/clientes";

function hideLoader() {
    document.getElementById("loading").style.display = "none";
}

function show(clientes) {
    let tab = `<thead>
                <th scope="col">#</th>
                <th scope="col">NOME</th>
                <th scope="col">EMAIL</th>
                <th scope="col">DATA_NASC</th>
                <th scope="col">CPF</th>
               </thead>`;
    
    for (let cliente of clientes) {
        tab+= `
                <tr>
                    <td scope="row">${cliente.id}</td>
                    <td>${cliente.nome}</td>
                    <td>${cliente.email}</td>
                    <td>${cliente.dataNasc}</td>
                    <td>${cliente.cpf}</td>
                    <td><button type="button" onclick="getCliente(${cliente.id})" class="btn btn-primary">Alterar</button></td>
                    <td><button type="button" onclick="deleteCliente(${cliente.id})" class="btn btn-danger">Delete</button></td>
                </tr> `;
    }

    document.getElementById("clientes").innerHTML = tab;
}

function alterar(cliente) {
    tab = `<form>
                <div class="mb-3">
                    <label class="form-label">Nome</label>
                    <input type="text" value="${cliente.nome}" class="form-control" id="nomeInput" aria-describedby="form-control" autocomplete="off">
                </div>
                <div class="mb-3">
                    <label class="form-label">Email</label>
                    <input type="text" value="${cliente.email}" class="form-control" id="emailInput">
                </div>
                <div class="mb-3">
                    <label class="form-label">Data_Nascimento</label>
                    <input type="text" value="${cliente.dataNasc}" class="form-control" id="dataNascInput">
                </div>
                <div class="mb-3">
                    <label class="form-label">CPF</label>
                    <input type="text" value=${cliente.cpf} class="form-control" id="cpfInput">
                </div>
                <button type="button" onclick="setCliente(${cliente.id})" class="btn btn-primary">Submit</button>
            </form>`;

            document.getElementById("clientes").innerHTML = tab;
}

async function getClientes() {
    let key = "Authorization";
    const response = await fetch(clienteEndpoint, {
        method: "GET",
        headers: new Headers({
            Authorization: localStorage.getItem(key),

        }),
    });
    
    var data = await response.json();
    console.log(data);
    if(response) hideLoader();
    show(data);
}

async function getCliente(id) {
    const clienteEndpoint1 = "http://localhost:8080/cliente/" + id;
    let key = "Authorization";
    const response = await fetch(clienteEndpoint1, {
        method: "GET",
        headers: new Headers({
            Authorization: localStorage.getItem(key),

        }),
    });

    var data = await response.json();
    console.log(data);
    if (response) hideLoader();
    alterar(data);
}

async function deleteCliente(clienteId){
    let key = "Authorization";
    const response = await fetch("http://localhost:8080/cliente/" + clienteId , {
      method: "DELETE",
      headers: new Headers({
        Authorization: localStorage.getItem(key),
      }),
    });
    window.location = "/view/cliente.html";
}

async function setCliente(id){


    const clienteEndpoint1 = "http://localhost:8080/cliente/" + id;
    let nome = document.getElementById("nomeInput").value;
    let email = document.getElementById("emailInput").value;
    let dataNasc = document.getElementById("dataNascInput").value;
    let cpf = document.getElementById("cpfInput").value; 
    let key = "Authorization";
    const response = await fetch(clienteEndpoint1, {
        method: "PUT",
        headers: new Headers({
            "Content-Type": "application/json; charset=utf8",
            Accept: "application/json",
            Authorization: localStorage.getItem(key),

        }),
        body: JSON.stringify({
            nome: nome,
            email: email,
            dataNasc: dataNasc,
            cpf: cpf
        }),
    });
    window.location = "/view/cliente.html";

}

document.addEventListener("DOMContentLoaded", function (event) {
    if (!localStorage.getItem("Authorization"))
      window.location = "/view/login.html";
  });

getClientes();