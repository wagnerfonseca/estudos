/*
Um modulo consiste em utilizar Closures, funções de primeira-classe e objetos literais.

*/ 

function printableMessage() {

    var message = 'hello';

    function setMessage(newMessage) {
        if (!newMessage) throw new Error('cannot set empty message');
        message = newMessage;
    }

    function getMessage() {
        return message;
    }

    function printMessage() {
        console.log(message);
    }

    return {
        setMessage: setMessage,
        getMessage: getMessage,
        printMessage: printMessage
    };

}

var awesome1 = printableMessage();
awesome1.printMessage();

var awesome2 = printableMessage();
awesome2.setMessage('Hi!');
awesome2.printMessage();


awesome1.printMessage();