/**
 * Se você deseja que um obejto seja criado a cada vez que o require é chamado 
 * 
 * Você pode exportar uma função no modulo de origem que retorna um objeto.
 */

module.exports = function() {
    return {
        something: 123
    };
};