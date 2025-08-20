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
function mostrarFormularioPerfil() {
    document.getElementById("vistaPerfil").style.display = "none";
    document.getElementById("formularioPerfil").style.display = "block";
    document.getElementById("btnEditarPerfil").style.display = "none";
}

function cancelarEdicionPerfil() {
    document.getElementById("formularioPerfil").style.display = "none";
    document.getElementById("vistaPerfil").style.display = "block";
    document.getElementById("btnEditarPerfil").style.display = "inline-block";
}


function mostrarFormulario() {
    document.getElementById("vistaPyme").style.display = "none";
    document.getElementById("formularioPyme").style.display = "block";
}

function cancelarEdicion() {
    document.getElementById("formularioPyme").style.display = "none";
    document.getElementById("vistaPyme").style.display = "block";
}

document.addEventListener("DOMContentLoaded", function () {
    const estrellas = document.querySelectorAll('#estrellas i');
    const inputRating = document.getElementById('rating');
    estrellas.forEach(estrella => {
        estrella.addEventListener('click', () => {
            const valor = estrella.getAttribute('data-value');
            inputRating.value = valor;
            actualizarEstrellas(valor);
        });
    });
    function actualizarEstrellas(valor) {
        estrellas.forEach(e => {
            const v = e.getAttribute('data-value');
            e.classList.remove('fa-solid', 'text-warning');
            e.classList.add('fa-regular');
            if (v <= valor) {
                e.classList.add('fa-solid', 'text-warning');
                e.classList.remove('fa-regular');
            }
        });
    }

    if (inputRating.value) {
        actualizarEstrellas(inputRating.value);
    }
});
function setEliminarUrl(url) {
    const boton = document.getElementById('btnConfirmarEliminar');
    boton.setAttribute('href', url);
}


function responder() {
    const input = document.getElementById("user-input").value.toLowerCase().trim();
    const chat = document.getElementById("chat");
    let respuesta = "Lo siento, no tengo información sobre eso. Te recomiendo revisar las preguntas frecuentes o contactar a soporte.";
    const respuestas = {
        "factura": "Para facturar electrónicamente, ingresa al menú Facturación y haz clic en 'Nueva factura'.",
        "correo": "Para cambiar tu correo, ve a tu perfil y haz clic en 'Editar'.",
        "plan": "Actualmente ofrecemos planes para pymes, emprendedores y contadores.",
        "certificado": "El certificado se descarga desde tu cuenta del Ministerio de Hacienda.",
        "firma digital": "La firma digital se configura desde tu navegador siguiendo las instrucciones del BCCR."
    };
    // Buscar una respuesta por coincidencia de palabra clave
    for (const clave in respuestas) {
        if (input.includes(clave)) {
            respuesta = respuestas[clave];
            break;
        }
    }

    chat.innerHTML += `<p><strong>Usted:</strong> ${input}</p><p><strong>Soporte Virtual:</strong> ${respuesta}</p>`;
    document.getElementById("user-input").value = "";
}

document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById('iaForm');
    const btnGenerate = document.getElementById('btnGenerate');
    const micBtn = document.getElementById("btnHablar");
    const textarea = document.getElementById("prompt");

    const resultModal = document.getElementById('resultModal') ? new bootstrap.Modal(document.getElementById('resultModal')) : null;
    const errorModal = document.getElementById('errorModal') ? new bootstrap.Modal(document.getElementById('errorModal')) : null;
    const resultText = document.getElementById('resultText');
    const errorText = document.getElementById('errorText');

    if (form) {
        form.addEventListener('submit', async (e) => {
            e.preventDefault();
            btnGenerate.disabled = true;
            const saved = btnGenerate.textContent;
            btnGenerate.textContent = saved + '…';

            try {
                const prompt = textarea.value.trim();
                const resp = await fetch('/ai/generate', {
                    method: 'POST',
                    headers: {'Content-Type': 'application/json'},
                    body: JSON.stringify({prompt})
                });
                const data = await resp.json();

                if (data.ok) {
                    if (resultText)
                        resultText.textContent = data.text || '(sin contenido)';
                    if (resultModal)
                        resultModal.show();
                } else {
                    if (errorText)
                        errorText.textContent = data.error || 'Error desconocido';
                    if (errorModal)
                        errorModal.show();
                }
            } catch (err) {
                if (errorText)
                    errorText.textContent = err?.message || 'Fallo de red';
                if (errorModal)
                    errorModal.show();
            } finally {
                btnGenerate.disabled = false;
                btnGenerate.textContent = saved;
            }
        });
    }

    
    (function initVoice() {
        const micBtn = document.getElementById("btnHablar");
        const textarea = document.getElementById("prompt");
        if (!micBtn || !textarea)
            return;

        const Recognition = window.SpeechRecognition || window.webkitSpeechRecognition;
        if (!Recognition) {
            console.warn("Reconocimiento de voz no soportado en este navegador.");
            return;
        }

        const rec = new Recognition();
        rec.lang = "es-ES";        
        rec.continuous = false;    
        rec.interimResults = false;

        micBtn.addEventListener("click", () => {
            try {
                rec.start();
                micBtn.dataset.prevBg = micBtn.style.backgroundColor;
                micBtn.style.backgroundColor = "#e53e3e"; 
            } catch (e) {
                console.warn("No se pudo iniciar el micrófono:", e);
            }
        });

        rec.onresult = (event) => {
            const texto = event.results[0][0].transcript;

            textarea.value = texto;

        };

        rec.onerror = (e) => {
            console.error("Error de voz:", e.error);
        };

        rec.onend = () => {
            micBtn.style.backgroundColor = micBtn.dataset.prevBg || "#4fd1c5";
        };
    })();
    
document.addEventListener("click", (e) => {
  const link = e.target.closest("a.fill-prompt");
  if (!link) return;

  e.preventDefault();

  const textarea = document.getElementById("prompt");
  if (!textarea) {
    console.warn("No se encontró #prompt en el DOM");
    return;
  }

  const prompt =
    (link.dataset.prompt && link.dataset.prompt.trim()) ||
    (link.textContent && link.textContent.trim()) ||
    "";

  if (!prompt) return;

  const MODE = "REPLACE"; // 

  if (MODE === "APPEND") {
    textarea.value += (textarea.value ? "\n" : "") + prompt;
  } else {
    textarea.value = prompt;
  }

  textarea.focus();
  const end = textarea.value.length;
  textarea.setSelectionRange(end, end);
});






});
