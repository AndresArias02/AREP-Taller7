document.addEventListener("DOMContentLoaded", () => {
    const name = sessionStorage.getItem("userName");
    const token = sessionStorage.getItem("token");

    if (!token) {
        alert("Inicia sesión para acceder a esta página");
        window.location.href = "index.html";
        return;
    }

    // Formulario para publicar un mensaje
    document.getElementById("postForm").addEventListener("submit", async (event) => {
        event.preventDefault();
        const message = document.getElementById("message").value;
        const post = {
            username: name,
            content: message,
        };

        try {
            const response = await fetch("/api/v1/post", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}`,
                },
                body: JSON.stringify(post),
            });

            if (response.status === 201) {
                fetchPosts(); // Cargar los posts después de publicar uno nuevo
            } else {
                alert("Error al publicar mensaje");
            }
        } catch (error) {
            console.error("Error:", error);
        }
    });

    // Función para obtener los posts desde el servidor
    async function fetchPosts() {
        try {
            const response = await fetch('/api/v1/post', {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    "Authorization": `Bearer ${token}`,
                }
            });

            if (!response.ok) {
                throw new Error('Error al obtener los posts: ' + response.status);
            }

            const posts = await response.json();
            console.log('Respuesta del servidor:', posts); // Verifica la respuesta aquí

            displayPosts(posts);
        } catch (error) {
            console.error('Error:', error);
        }
    }

    // Función para mostrar los posts en el DOM
    function displayPosts(posts) {
        const postsContainer = document.getElementById('posts-container');
        postsContainer.innerHTML = ''; // Limpiar contenido previo

        // Verificar si 'posts' es un array y tiene elementos
        if (Array.isArray(posts) && posts.length > 0) {
            posts.forEach(post => {
                const postElement = document.createElement('div');
                postElement.classList.add('post');

                // Comprobamos si los campos son válidos antes de mostrarlos
                const username = post.username || 'Usuario desconocido';
                const creationDate = post.creationDate || 'Fecha no disponible';
                const content = post.content || 'Contenido no disponible';

                postElement.innerHTML = `
                    <h3>${username}</h3>
                    <p>${creationDate}</p>
                    <p>${content}</p>
                `;

                postsContainer.appendChild(postElement);
            });
        } else {
            postsContainer.innerHTML = '<p>No hay posts disponibles</p>';
        }
    }

    // Llamar a fetchPosts para cargar los posts cuando la página se haya cargado
    fetchPosts();
});
