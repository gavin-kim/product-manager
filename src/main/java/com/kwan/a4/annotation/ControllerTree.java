package com.kwan.a4.annotation;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Stream;


/**
 * ControllerTree has references to @Mapping annotations in the package
 * This class helps to find a method annotated @Mapping.
 */
public class ControllerTree {

    private ControllerNode root = new ControllerNode();

    public ControllerTree(String packageName) {
        createTree(packageName);
    }

    /**
     * Get a method that has @Mapping(value = url, method = httpMethod) in the tree
     *
     * @param url        path
     * @param httpMethod Http request method
     * @return Method object
     */
    public Method getMethod(String url, HttpMethod httpMethod) {

        if (url == null)
            return null;

        String[] tokens = url.split("/");
        Node node = root;

        for (int i = 1; i < tokens.length; i++) {

            node = node.getChild("/" + tokens[i]);

            if (node == null)
                return null;
        }

        AnnotatedElement el = ((ControllerNode) node).getElement(httpMethod);

        return el instanceof Method ? (Method) el : null;
    }

    public void printTree() {

        printTreeHelper(root);
    }

    private void printTreeHelper(ControllerNode node) {

        node.elements.values().forEach(element -> {
            System.out.println(node.getPath() + ", " + element);
        });

        node.getChildren().forEach(n -> {
            printTreeHelper((ControllerNode) n);
        });
    }

    /**
     * Create a tree. It consists of @Mapping nodes
     *
     * @param packageName a full package name
     */
    private void createTree(String packageName) {

        try {
            PackageScanner.INSTANCE.scan(packageName).stream()
                .filter(classEl -> classEl.isAnnotationPresent(Mapping.class))
                .forEach(classEl -> {

                    Mapping mappingC = classEl.getAnnotation(Mapping.class);

                    ControllerNode node = new ControllerNode(classEl, root, mappingC);

                    root.addChild(node);

                    Stream.of(classEl.getDeclaredMethods())
                        .filter(methodEl -> methodEl.isAnnotationPresent(Mapping.class))
                        .forEach(methodEl -> {

                            Mapping mappingM = methodEl.getAnnotation(Mapping.class);
                            String key = mappingM.value();

                            if (key.equals("/")) {

                                node.addElement(mappingM.method(), methodEl);

                            }
                            // if the node has the same key, just add element in the existing node.
                            else if (node.hasChild(mappingM.value())) {

                                ((ControllerNode) node.getChild(key))
                                    .addElement(mappingM.method(), methodEl);
                            } else {
                                node.addChild(new ControllerNode(methodEl, node, mappingM));
                            }
                        });
                });

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private class ControllerNode extends Node {

        private Map<HttpMethod, AnnotatedElement> elements = new EnumMap<>(HttpMethod.class);

        public ControllerNode() {
            super();
            elements.put(HttpMethod.GET, null);
        }

        public ControllerNode(AnnotatedElement element, Node parent, Mapping mapping) {
            super(parent, mapping.value());
            elements.put(mapping.method(), element);
        }

        public void addElement(HttpMethod httpMethod, AnnotatedElement element) {
            elements.put(httpMethod, element);
        }

        public AnnotatedElement getElement(HttpMethod httpMethod) {
            return elements.get(httpMethod);
        }

        public AnnotatedElement removeElement(HttpMethod httpMethod) {
            return elements.remove(httpMethod);
        }
    }
}
