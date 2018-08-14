package com.demo.other;

public class NodeTree {

    Node root;


    class Node<E> {
        private Node left;

        private Node right;

        private E element;

        public Node(Node left, Node right, E element) {
            this.left = left;
            this.right = right;
            this.element = element;
        }
    }



    public boolean addNode(Integer element){
        Node<Integer> node = this.root;

       Node<Integer> newNode = new Node<Integer>(null,null,element);

        //如果root为空
        if(node==null){
            this.root = new Node<Integer>(null,null,element);
        }else{
            //存在root节点,循环遍历parent
            while(node!=null){
                System.out.println(node);
                if(node.element>newNode.element){
                    //左子树
                    if(node.left==null){
                        node.left = newNode;
                        break;
                    }else
                    node = node.left;}
                else{
                    //左子树
                    if(node.right==null){
                        node.right = newNode;
                        break;
                    }else
                    node = node.right;
                }
            }
            //当node为空,说明该处没有节点,则添加

        }

        return true;
    }


    public void printTree(Node<Integer> node){
      if(this.root==null)
          return;
      else{

        if(node.left!=null)
            printTree(node.left);

        System.out.println(node.element);

        if(node.right!=null)
            printTree(node.right);
      }

    }

    public static void main(String[] args){
        NodeTree tree  = new NodeTree();
        tree.addNode(7);

        tree.addNode(6);
        tree.addNode(9);
        tree.addNode(8);

        tree.addNode(1);


        tree.printTree(tree.root);
    }


}
