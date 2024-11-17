const baseUrl = 'http://localhost:8080/api/v1/catcher';

document.addEventListener('DOMContentLoaded', (event) => {
    const responseDiv = document.getElementById('response');
    responseDiv.addEventListener('click', (event) => {
        if (event.button === 0) { // left mouse
            const rect = responseDiv.getBoundingClientRect();

            const xPos = Math.round(event.clientX - rect.left);
            const yPos = Math.round(event.clientY - rect.top);
            const screenX = Math.round(rect.width);
            const screenY = Math.round(rect.height);

            const url = `${baseUrl}/getPictureXY?xMouse=${xPos}&yMouse=${yPos}&screenWidth=${screenX}&screenHeight=${screenY}`;

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
                    img.alt = `Panel for ${xPos}x${yPos}`;
                    const responseContainer = document.getElementById('response');
                    responseContainer.innerHTML = '';
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