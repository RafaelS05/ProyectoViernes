function toggleBN() {
    const btn = document.getElementById('bn-toggle');
    const estado = document.getElementById('modo-estado');

    btn.classList.toggle('on');
    btn.classList.toggle('off');

    if (btn.classList.contains('off')) {
        document.body.classList.add('modo-blanco-negro');
    } else {
        estado.innerText = 'on';
        document.body.classList.remove('modo-blanco-negro');
    }
}

// Estilo opcional para blanco y negro
const estiloBN = document.createElement('style');
estiloBN.innerHTML = `
    .modo-blanco-negro {
        filter: grayscale(100%) !important;
    }
`;
document.head.appendChild(estiloBN);

function vistaPrevia(event) {
    const input = event.target;
    const img = document.getElementById('imagenPreview');

    if (input.files && input.files[0]) {
        const reader = new FileReader();
        reader.onload = function (e) {
            img.src = e.target.result;
            img.style.display = 'block';
        };
        reader.readAsDataURL(input.files[0]);
    }
}

function cargarAvatar(event) {
    const input = event.target;
    const preview = document.getElementById('avatarPreview');

    if (input.files && input.files[0]) {
        const reader = new FileReader();
        reader.onload = e => {
            preview.src = e.target.result;
        };
        reader.readAsDataURL(input.files[0]);
    }
}


function mostrarFormulario() {
    document.getElementById("vistaPerfil").style.display = "none";
    document.getElementById("formularioPerfil").style.display = "block";
    document.getElementById("btnEditarPerfil").style.display = "none";
}

function cancelarEdicion() {
    document.getElementById("formularioPerfil").style.display = "none";
    document.getElementById("vistaPerfil").style.display = "block";
    document.getElementById("btnEditarPerfil").style.display = "inline-block";
}

