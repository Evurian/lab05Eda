package proyect;
public class Nodo<T> {
    private T data;
    private Nodo<T> left;
    private Nodo<T> right;
    private int altura;

    public Nodo(T data) {
        this.data = data;
        this.left = null;
        this.right = null;
        this.altura = 1;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setLeft(Nodo<T> left) {
        this.left = left;
    }

    public Nodo<T> getLeft() {
        return left;
    }

    public void setRight(Nodo<T> right) {
        this.right = right;
    }

    public Nodo<T> getRight() {
        return right;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getAltura() {
        return altura;
    }

    // Método para actualizar la altura del nodo
    public void actualizarAltura() {
        int alturaIzquierda = (left == null) ? 0 : left.getAltura();
        int alturaDerecha = (right == null) ? 0 : right.getAltura();
        altura = 1 + Math.max(alturaIzquierda, alturaDerecha);
    }

    // Método para obtener el factor de balance del nodo
    public int balance() {
        int alturaIzquierda = (left == null) ? 0 : left.getAltura();
        int alturaDerecha = (right == null) ? 0 : right.getAltura();
        return alturaIzquierda - alturaDerecha;
    }
}
