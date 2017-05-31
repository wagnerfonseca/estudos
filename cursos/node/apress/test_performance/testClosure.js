function longRunningOperation(callback) {
    // simulate a 3 second operation
    setTimeout(callback, 5000);
}

function webRequest(request) {
    console.log('starting a long operation for request:', request.id);
    longRunningOperation(function () {
        console.log('ending a long operation for request:', request.id);
    });
}

// simulate a web request
webRequest({ id: 1 });
// simulate a second web request
webRequest({ id: 2 });


/// Com closure temos acesso a requisição do usuário corrente.