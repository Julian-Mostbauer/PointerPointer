document.addEventListener('DOMContentLoaded', (event) => {
    document.addEventListener('click', (event) => {
        if (event.button === 0) { // Check if the left mouse button was clicked
            const xPos = event.clientX;
            const yPos = event.clientY;
            const screenX = window.screen.width;
            const screenY = window.screen.height;

            const url = `http://localhost:8080/getPictureXY?xMouse=${xPos}&yMouse=${yPos}&screenWidth=${screenX}&screenHeight=${screenY}`;

            console.table({xPos, yPos, screenX, screenY, url});

            fetch(url)
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    return response.blob();
                })
                .then(blob => {
                    const img = document.createElement('img');
                    img.src = URL.createObjectURL(blob);
                    img.alt = `Panel ${xPos}x${yPos}`;
                    const responseContainer = document.getElementById('response');
                    responseContainer.innerHTML = ''; // Clear previous content
                    responseContainer.appendChild(img);
                })
                .catch(error => {
                    let el = document.getElementById('response');
                    el.innerHTML = `Error: ${error.message}`;
                    el.style.color = 'red';
                });
        }
    });
});