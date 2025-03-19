function removeMessage() {
    document.addEventListener("DOMContentLoaded", function () {
        setTimeout(function () {
            let messageDiv = document.getElementById("flash-message");
            if (messageDiv) {
                messageDiv.style.transition = "opacity 0.5s ease-out";
                messageDiv.style.opacity = "0";
                setTimeout(() => messageDiv.remove(), 300);
            }
        }, 3000);
    });

}

removeMessage();