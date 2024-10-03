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
                </tr> `;
    }

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

document.addEventListener("DOMContentLoaded", function (event) {
    if (!localStorage.getItem("Authorization"))
      window.location = "/view/login.html";
  });

getClientes();