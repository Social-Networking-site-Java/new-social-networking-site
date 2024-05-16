const chatMessages = document.getElementById('chatMessages');
const messageInput = document.getElementById('messageInput');
const sendButton = document.getElementById('sendButton');

// WebSocket connection
const socket = new WebSocket('ws://localhost:5000/chat');

socket.onopen = () => {
    console.log('WebSocket connection established');
};

socket.onmessage = (event) => {
    const message = JSON.parse(event.data);
    const messageElement = document.createElement('div');
    messageElement.textContent = `${message.sender}: ${message.message}`;
    chatMessages.appendChild(messageElement);
};

socket.onclose = () => {
    console.log('WebSocket connection closed');
};

sendButton.addEventListener('click', sendMessage);
messageInput.addEventListener('keyup', function (event) {
    if (event.key === 'Enter') {
        sendMessage();
    }
});

function sendMessage() {
    const message = messageInput.value.trim();
    if (message !== '') {
        const payload = {
            sender: 'Your Username', // Replace with the actual username
            recipient: 'Recipient Username', // Replace with the recipient's username
            message: message
        };
        socket.send(JSON.stringify(payload));
        messageInput.value = '';
    }
}