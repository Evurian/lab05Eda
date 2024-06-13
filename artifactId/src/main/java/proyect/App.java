package proyect;

public class App {
    public static void main(String[] args) {
        System.setProperty("org.graphstream.ui", "swing");

        AVL<Integer> tree = new AVL<>();
        tree.insertar(50);
        tree.insertar(30);
        tree.insertar(20);
        tree.insertar(40);
        tree.insertar(70);
        tree.insertar(60);
        tree.insertar(80);
        tree.insertar(10);
        tree.insertar(5);
        tree.insertar(15);
        tree.insertar(35);
        tree.insertar(45);
        tree.insertar(65);
        tree.insertar(75);
        tree.insertar(90);

        

        tree.visualizar();
    }
}

