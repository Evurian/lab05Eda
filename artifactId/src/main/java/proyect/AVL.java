package proyect;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import java.util.ArrayList;

public class AVL<T extends Comparable<T>> {
    private Nodo<T> root;

    public AVL() {
        this.root = null;
    }

    // SEARCH
    public boolean search(T data) {
        return searchRec(root, data);
    }

    private boolean searchRec(Nodo<T> node, T data) {
        if (node == null)
            return false;

        if (data.compareTo(node.getData()) == 0)
            return true;
        else if (data.compareTo(node.getData()) < 0)
            return searchRec(node.getLeft(), data);
        else
            return searchRec(node.getRight(), data);
    }

    // GET MIN
    public T getMin() {
        if (root == null)
            return null;

        Nodo<T> current = root;
        while (current.getLeft() != null)
            current = current.getLeft();

        return current.getData();
    }

    // GET MAX
    public T getMax() {
        if (root == null)
            return null;

        Nodo<T> current = root;
        while (current.getRight() != null)
            current = current.getRight();

        return current.getData();
    }

    // PARENT
    public Nodo<T> parent(T data) {
        return parentRec(root, data);
    }

    private Nodo<T> parentRec(Nodo<T> node, T data) {
        if (node == null)
            return null;

        if ((node.getLeft() != null && node.getLeft().getData().equals(data)) || 
            (node.getRight() != null && node.getRight().getData().equals(data)))
            return node;

        if (data.compareTo(node.getData()) < 0)
            return parentRec(node.getLeft(), data);
        else
            return parentRec(node.getRight(), data);
    }

    // SON
    public ArrayList<Nodo<T>> son(Nodo<T> nodo) {
        ArrayList<Nodo<T>> hijos = new ArrayList<>();
        
        if (nodo != null) {
            if (nodo.getLeft() != null)
                hijos.add(nodo.getLeft());
            if (nodo.getRight() != null)
                hijos.add(nodo.getRight());
        }
        
        return hijos;
    }


    // INSERT
    public void insertar(T data) {
        root = insertarRec(root, data);
    }

    private Nodo<T> insertarRec(Nodo<T> node, T data) {
        if (node == null)
            return new Nodo<>(data);
        
        if (data.compareTo(node.getData()) < 0)
            node.setLeft(insertarRec(node.getLeft(), data));
        else if (data.compareTo(node.getData()) > 0)
            node.setRight(insertarRec(node.getRight(), data));
        else
            return node; // No se permiten duplicados

        // Actualizar la altura del nodo actual
        node.actualizarAltura();

        // Realizar rotaciones si es necesario para mantener el balance
        int balance = node.balance();
        if (balance > 1 && data.compareTo(node.getLeft().getData()) < 0)
            return rotacionDerecha(node);
        if (balance < -1 && data.compareTo(node.getRight().getData()) > 0)
            return rotacionIzquierda(node);
        if (balance > 1 && data.compareTo(node.getLeft().getData()) > 0) {
            node.setLeft(rotacionIzquierda(node.getLeft()));
            return rotacionDerecha(node);
        }
        if (balance < -1 && data.compareTo(node.getRight().getData()) < 0) {
            node.setRight(rotacionDerecha(node.getRight()));
            return rotacionIzquierda(node);
        }

        return node;
    }

    // DELETE
    public void eliminar(T data) {
        root = eliminarRec(root, data);
    }

    private Nodo<T> eliminarRec(Nodo<T> node, T data) {
        if (node == null)
            return null;

        if (data.compareTo(node.getData()) < 0)
            node.setLeft(eliminarRec(node.getLeft(), data));
        else if (data.compareTo(node.getData()) > 0)
            node.setRight(eliminarRec(node.getRight(), data));
        else {
            if (node.getLeft() == null || node.getRight() == null) {
                Nodo<T> temp = null;
                if (temp == node.getLeft())
                    temp = node.getRight();
                else
                    temp = node.getLeft();
                
                if (temp == null) {
                    temp = node;
                    node = null;
                } else
                    node = temp;
            } else {
                Nodo<T> temp = minimoValorNodo(node.getRight());
                node.setData(temp.getData());
                node.setRight(eliminarRec(node.getRight(), temp.getData()));
            }
        }

        if (node == null)
            return null;

        node.actualizarAltura();

        /* balancear */
        int balance = node.balance();
        if (balance > 1 && node.getLeft().balance() >= 0)
            return rotacionDerecha(node);
        if (balance > 1 && node.getLeft().balance() < 0) {
            node.setLeft(rotacionIzquierda(node.getLeft()));
            return rotacionDerecha(node);
        }
        if (balance < -1 && node.getRight().balance() <= 0)
            return rotacionIzquierda(node);
        if (balance < -1 && node.getRight().balance() > 0) {
            node.setRight(rotacionDerecha(node.getRight()));
            return rotacionIzquierda(node);
        }

        return node;
    }

    private Nodo<T> minimoValorNodo(Nodo<T> node) {
        Nodo<T> current = node;
        while (current.getLeft() != null)
            current = current.getLeft();
        return current;
    }

    // ROTATE DER
    private Nodo<T> rotacionDerecha(Nodo<T> y) {
        Nodo<T> x = y.getLeft();
        Nodo<T> T2 = x.getRight();

        x.setRight(y);
        y.setLeft(T2);

        y.actualizarAltura();
        x.actualizarAltura();

        return x;
    }

    // ROTATE IZQ
    private Nodo<T> rotacionIzquierda(Nodo<T> x) {
        Nodo<T> y = x.getRight();
        Nodo<T> T2 = y.getLeft();

        y.setLeft(x);
        x.setRight(T2);

        x.actualizarAltura();
        y.actualizarAltura();

        return y;
    }

    // Method to print the AVL tree
    public void imprimirArbol() {
        if (root != null) {
            imprimirArbolRec(root, 0);
        }
    }

    // Recursive method to print the AVL tree
    private void imprimirArbolRec(Nodo<T> node, int nivel) {
        if (node != null) {
            imprimirArbolRec(node.getRight(), nivel + 1);
            for (int i = 0; i < nivel; i++) {
                System.out.print("    ");
            }
            System.out.println(node.getData());
            imprimirArbolRec(node.getLeft(), nivel + 1);
        }
    }


    
    public void visualizar() {
        Graph graph = new SingleGraph("AVL Tree");
        graph.setAttribute("ui.stylesheet", styleSheet);
        graph.setAutoCreate(true);
        graph.setStrict(false);

        visualizarRec(root, graph, 0, 0, 0);

        for (Node node : graph) {
            node.setAttribute("ui.label", node.getId());
        }

        graph.display();
    }

    private void visualizarRec(Nodo<T> node, Graph graph, int x, int y, int level) {
        System.out.println("a");
        System.out.println(x);
        System.out.println(y);
        if (node != null) {
            String id = node.getData().toString();
            graph.addNode(id).setAttribute("xy", x, -y);

            if (node.getLeft() != null) {
                String leftId = node.getLeft().getData().toString();
                graph.addEdge(id + leftId, id, leftId, true);
                visualizarRec(node.getLeft(), graph, x - (1 << (4 - level)), y + 1, level + 1);
                System.out.println("L");
                
            }

            if (node.getRight() != null) {
                String rightId = node.getRight().getData().toString();
                graph.addEdge(id + rightId, id, rightId, true);
                visualizarRec(node.getRight(), graph, x + (1 << (4 - level)), y + 1, level + 1);
                System.out.println("R");
                
            }
        }
    }

    private static String styleSheet = 
        "node {" +
        "   fill-color: orange;" +
        "   size: 30px, 30px;" +
        "   text-size: 20;" +
        "   text-color: black;" +
        "   text-background-mode: plain;" +
        "   text-background-color: white;" +
        "   text-alignment: center;" +
        "}" +
        "edge {" +
        "   fill-color: black;" +
        "}";

    
}
