import java.util.Random;

/////////////////////////////////binary tree//////////////////////
class BinaryTree {

	Node root;

	class Node {
		int value;
		Node left;
		Node right;

		Node(int value) {
			this.value = value;
			right = null;
			left = null;
		}
	}

	public void add(int value) {
		root = addRecursive(root, value);
	}

	Node addRecursive(Node current, int value) {

		if (current == null) {
			return new Node(value);
		}

		if (value < current.value) {
			current.left = addRecursive(current.left, value);
		} else if (value > current.value) {
			current.right = addRecursive(current.right, value);
		} else {
			// value already exists
			return current;
		}

		return current;
	}

	public boolean isEmpty() {
		return root == null;
	}

	public Node find(int key) {
		Node current = root;
		while (current != null) {
			if (current.value == key) {
				break;
			}
			current = current.value < key ? current.right : current.left;
		}
		return current;
	}

	public int findComparsionNum(int key) {
		Node current = root;
		int ComparsionNum = 0;
		while (current != null) {
			ComparsionNum++;
			if (current.value == key) {
				break;
			}
			current = current.value < key ? current.right : current.left;
		}
		return ComparsionNum;
	}

	public void traverseInOrder(Node node) {
		if (node != null) {
			traverseInOrder(node.left);
			System.out.print(" " + node.value);
			traverseInOrder(node.right);
		}
	}

	public void traversePreOrder(Node node) {
		if (node != null) {
			System.out.print(" " + node.value);
			traversePreOrder(node.left);
			traversePreOrder(node.right);
		}
	}

	public void traversePostOrder(Node node) {
		if (node != null) {
			traversePostOrder(node.left);
			traversePostOrder(node.right);
			System.out.print(" " + node.value);
		}
	}

}
/////////////////////////////////binary tree//////////////////////
/////////////////////////////////AVL tree//////////////////////

class AVLTree {

	public class Node {
		int key;
		int height;
		Node left;
		Node right;

		Node(int key) {
			this.key = key;
		}
	}

	Node root;

	public Node find(int key) {
		Node current = root;
		while (current != null) {
			if (current.key == key) {
				break;
			}
			current = current.key < key ? current.right : current.left;
		}
		return current;
	}

	public int findComparsionNum(int key) {
		Node current = root;
		int ComparsionNum = 0;
		while (current != null) {
			ComparsionNum++;
			if (current.key == key) {
				break;
			}
			current = current.key < key ? current.right : current.left;
		}
		return ComparsionNum;
	}

	public void insert(int key) {
		root = insert(root, key);
	}

	public void delete(int key) {
		root = delete(root, key);
	}

	public Node getRoot() {
		return root;
	}

	public int height() {
		return root == null ? -1 : root.height;
	}

	Node insert(Node node, int key) {
		if (node == null) {
			return new Node(key);
		} else if (node.key > key) {
			node.left = insert(node.left, key);
		} else if (node.key < key) {
			node.right = insert(node.right, key);
		} else {
			// value already exists
			return node;
		}
		return rebalance(node);
	}

	private Node delete(Node node, int key) {
		if (node == null) {
			return node;
		} else if (node.key > key) {
			node.left = delete(node.left, key);
		} else if (node.key < key) {
			node.right = delete(node.right, key);
		} else {
			if (node.left == null || node.right == null) {
				node = (node.left == null) ? node.right : node.left;
			} else {
				Node mostLeftChild = mostLeftChild(node.right);
				node.key = mostLeftChild.key;
				node.right = delete(node.right, node.key);
			}
		}
		if (node != null) {
			node = rebalance(node);
		}
		return node;
	}

	private Node mostLeftChild(Node node) {
		Node current = node;
		/* loop down to find the leftmost leaf */
		while (current.left != null) {
			current = current.left;
		}
		return current;
	}

	private Node rebalance(Node z) {
		updateHeight(z);
		int balance = getBalance(z);
		if (balance > 1) {
			if (height(z.right.right) > height(z.right.left)) {
				z = rotateLeft(z);
			} else {
				z.right = rotateRight(z.right);
				z = rotateLeft(z);
			}
		} else if (balance < -1) {
			if (height(z.left.left) > height(z.left.right)) {
				z = rotateRight(z);
			} else {
				z.left = rotateLeft(z.left);
				z = rotateRight(z);
			}
		}
		return z;
	}

	private Node rotateRight(Node y) {
		Node x = y.left;
		Node z = x.right;
		x.right = y;
		y.left = z;
		updateHeight(y);
		updateHeight(x);
		return x;
	}

	private Node rotateLeft(Node y) {
		Node x = y.right;
		Node z = x.left;
		x.left = y;
		y.right = z;
		updateHeight(y);
		updateHeight(x);
		return x;
	}

	private void updateHeight(Node n) {
		n.height = 1 + Math.max(height(n.left), height(n.right));
	}

	private int height(Node n) {
		return n == null ? -1 : n.height;
	}

	public int getBalance(Node n) {
		return (n == null) ? 0 : height(n.right) - height(n.left);
	}

	public void preOrder(Node node) {
		if (node != null) {
			System.out.print(node.key + " ");
			preOrder(node.left);
			preOrder(node.right);
		}
	}

}

/////////////////////////////////AVL tree//////////////////////
/////////////////////////////////균형트리 공유 메소드//////////////////////

abstract class AbstractBinarySearchTree {

	public Node root;

	protected int size;

	protected abstract Node createNode(int value, Node parent, Node left, Node right);

	public Node search(int element) {
		Node node = root;
		while (node != null && node.value != null && node.value != element) {
			if (element < node.value) {
				node = node.left;
			} else {
				node = node.right;
			}
		}
		return node;
	}

	public int findComparsionNum(int element) {
		int ComparsionNum = 0;
		Node node = root;
		while (node != null && node.value != null && node.value != element) {
			ComparsionNum++;
			if (element < node.value) {
				node = node.left;
			} else {
				node = node.right;
			}
		}
		return ComparsionNum;
	}

	public Node insert(int element) {
		if (root == null) {
			root = createNode(element, null, null, null);
			size++;
			return root;
		}

		Node insertParentNode = null;
		Node searchTempNode = root;
		while (searchTempNode != null && searchTempNode.value != null) {
			insertParentNode = searchTempNode;
			if (element < searchTempNode.value) {
				searchTempNode = searchTempNode.left;
			} else if (element > searchTempNode.value) {
				searchTempNode = searchTempNode.right;
			} else {// 입력값이 중복되는 노드 제거하기 위한 코드
				break;
			}
			/*
			 * else{ searchTempNode = searchTempNode.right; }
			 * 
			 */
		}

		Node newNode = createNode(element, insertParentNode, null, null);
		if (insertParentNode.value > newNode.value) {
			insertParentNode.left = newNode;
		} else {
			insertParentNode.right = newNode;
		}

		size++;
		return newNode;
	}

	public Node insertinRedBlack(int element) {
		if (root == null) {
			root = createNode(element, null, null, null);
			size++;
			return root;
		}

		Node insertParentNode = null;
		Node searchTempNode = root;
		while (searchTempNode != null && searchTempNode.value != null) {
			insertParentNode = searchTempNode;
			if (element < searchTempNode.value) {
				searchTempNode = searchTempNode.left;
			} else {
				searchTempNode = searchTempNode.right;
			}

		}

		Node newNode = createNode(element, insertParentNode, null, null);
		if (insertParentNode.value > newNode.value) {
			insertParentNode.left = newNode;
		} else {
			insertParentNode.right = newNode;
		}

		size++;
		return newNode;
	}

	private Node transplant(Node nodeToReplace, Node newNode) {
		if (nodeToReplace.parent == null) {
			this.root = newNode;
		} else if (nodeToReplace == nodeToReplace.parent.left) {
			nodeToReplace.parent.left = newNode;
		} else {
			nodeToReplace.parent.right = newNode;
		}
		if (newNode != null) {
			newNode.parent = nodeToReplace.parent;
		}
		return newNode;
	}

	public boolean contains(int element) {
		return search(element) != null;
	}

	public int getSize() {
		return size;
	}

	public static class Node {
		public Node(Integer value, Node parent, Node left, Node right) {
			super();
			this.value = value;
			this.parent = parent;
			this.left = left;
			this.right = right;
		}

		public Integer value;
		public Node parent;
		public Node left;
		public Node right;

		public boolean isLeaf() {
			return left == null && right == null;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((value == null) ? 0 : value.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Node other = (Node) obj;
			if (value == null) {
				if (other.value != null)
					return false;
			} else if (!value.equals(other.value))
				return false;
			return true;
		}

	}
}

abstract class AbstractSelfBalancingBinarySearchTree extends AbstractBinarySearchTree {

	protected Node rotateLeft(Node node) {
		Node temp = node.right;
		temp.parent = node.parent;

		node.right = temp.left;
		if (node.right != null) {
			node.right.parent = node;
		}

		temp.left = node;
		node.parent = temp;

		// temp took over node's place so now its parent should point to temp
		if (temp.parent != null) {
			if (node == temp.parent.left) {
				temp.parent.left = temp;
			} else {
				temp.parent.right = temp;
			}
		} else {
			root = temp;
		}

		return temp;
	}

	protected Node rotateRight(Node node) {
		Node temp = node.left;
		temp.parent = node.parent;

		node.left = temp.right;
		if (node.left != null) {
			node.left.parent = node;
		}

		temp.right = node;
		node.parent = temp;

		// temp took over node's place so now its parent should point to temp
		if (temp.parent != null) {
			if (node == temp.parent.left) {
				temp.parent.left = temp;
			} else {
				temp.parent.right = temp;
			}
		} else {
			root = temp;
		}

		return temp;
	}

}

/////////////////////////////////균형트리 공유메소드//////////////////////
/////////////////////////////////RedBlackTree//////////////////////

class RedBlackTree extends AbstractSelfBalancingBinarySearchTree {

	protected enum ColorEnum {
		RED, BLACK
	};

	protected static final RedBlackNode nilNode = new RedBlackNode(null, null, null, null, ColorEnum.BLACK);

	@Override
	public Node insert(int element) {
		Node newNode = super.insertinRedBlack(element);
		newNode.left = nilNode;
		newNode.right = nilNode;
		root.parent = nilNode;
		insertRBFixup((RedBlackNode) newNode);
		return newNode;
	}

	@Override
	protected Node createNode(int value, Node parent, Node left, Node right) {
		return new RedBlackNode(value, parent, left, right, ColorEnum.RED);
	}

	@Override
	protected Node rotateLeft(Node node) {
		Node temp = node.right;
		temp.parent = node.parent;

		node.right = temp.left;
		if (node.right != nilNode) {
			node.right.parent = node;
		}

		temp.left = node;
		node.parent = temp;

		// temp took over node's place so now its parent should point to temp
		if (temp.parent != nilNode) {
			if (node == temp.parent.left) {
				temp.parent.left = temp;
			} else {
				temp.parent.right = temp;
			}
		} else {
			root = temp;
		}

		return temp;
	}

	@Override
	protected Node rotateRight(Node node) {
		Node temp = node.left;
		temp.parent = node.parent;

		node.left = temp.right;
		if (node.left != nilNode) {
			node.left.parent = node;
		}

		temp.right = node;
		node.parent = temp;

		// temp took over node's place so now its parent should point to temp
		if (temp.parent != nilNode) {
			if (node == temp.parent.left) {
				temp.parent.left = temp;
			} else {
				temp.parent.right = temp;
			}
		} else {
			root = temp;
		}

		return temp;
	}

	private Node rbTreeTransplant(Node nodeToReplace, Node newNode) {
		if (nodeToReplace.parent == nilNode) {
			this.root = newNode;
		} else if (nodeToReplace == nodeToReplace.parent.left) {
			nodeToReplace.parent.left = newNode;
		} else {
			nodeToReplace.parent.right = newNode;
		}
		newNode.parent = nodeToReplace.parent;
		return newNode;
	}

	private void deleteRBFixup(RedBlackNode x) {
		while (x != root && isBlack(x)) {

			if (x == x.parent.left) {
				RedBlackNode w = (RedBlackNode) x.parent.right;
				if (isRed(w)) { // case 1 - sibling is red
					w.color = ColorEnum.BLACK;
					((RedBlackNode) x.parent).color = ColorEnum.RED;
					rotateLeft(x.parent);
					w = (RedBlackNode) x.parent.right; // converted to case 2, 3 or 4
				}
				// case 2 sibling is black and both of its children are black
				if (isBlack(w.left) && isBlack(w.right)) {
					w.color = ColorEnum.RED;
					x = (RedBlackNode) x.parent;
				} else if (w != nilNode) {
					if (isBlack(w.right)) { // case 3 sibling is black and its left child is red and right child is
											// black
						((RedBlackNode) w.left).color = ColorEnum.BLACK;
						w.color = ColorEnum.RED;
						rotateRight(w);
						w = (RedBlackNode) x.parent.right;
					}
					w.color = ((RedBlackNode) x.parent).color; // case 4 sibling is black and right child is red
					((RedBlackNode) x.parent).color = ColorEnum.BLACK;
					((RedBlackNode) w.right).color = ColorEnum.BLACK;
					rotateLeft(x.parent);
					x = (RedBlackNode) root;
				} else {
					x.color = ColorEnum.BLACK;
					x = (RedBlackNode) x.parent;
				}
			} else {
				RedBlackNode w = (RedBlackNode) x.parent.left;
				if (isRed(w)) { // case 1 - sibling is red
					w.color = ColorEnum.BLACK;
					((RedBlackNode) x.parent).color = ColorEnum.RED;
					rotateRight(x.parent);
					w = (RedBlackNode) x.parent.left; // converted to case 2, 3 or 4
				}
				// case 2 sibling is black and both of its children are black
				if (isBlack(w.left) && isBlack(w.right)) {
					w.color = ColorEnum.RED;
					x = (RedBlackNode) x.parent;
				} else if (w != nilNode) {
					if (isBlack(w.left)) { // case 3 sibling is black and its right child is red and left child is black
						((RedBlackNode) w.right).color = ColorEnum.BLACK;
						w.color = ColorEnum.RED;
						rotateLeft(w);
						w = (RedBlackNode) x.parent.left;
					}
					w.color = ((RedBlackNode) x.parent).color; // case 4 sibling is black and left child is red
					((RedBlackNode) x.parent).color = ColorEnum.BLACK;
					((RedBlackNode) w.left).color = ColorEnum.BLACK;
					rotateRight(x.parent);
					x = (RedBlackNode) root;
				} else {
					x.color = ColorEnum.BLACK;
					x = (RedBlackNode) x.parent;
				}
			}

		}
	}

	private boolean isBlack(Node node) {
		return node != null ? ((RedBlackNode) node).color == ColorEnum.BLACK : false;
	}

	private boolean isRed(Node node) {
		return node != null ? ((RedBlackNode) node).color == ColorEnum.RED : false;
	}

	private void insertRBFixup(RedBlackNode currentNode) {
		// current node is always RED, so if its parent is red it breaks
		// Red-Black property, otherwise no fixup needed and loop can terminate
		while (currentNode.parent != root && ((RedBlackNode) currentNode.parent).color == ColorEnum.RED) {
			RedBlackNode parent = (RedBlackNode) currentNode.parent;
			RedBlackNode grandParent = (RedBlackNode) parent.parent;
			if (parent == grandParent.left) {
				RedBlackNode uncle = (RedBlackNode) grandParent.right;
				// case1 - uncle and parent are both red
				// re color both of them to black
				if (((RedBlackNode) uncle).color == ColorEnum.RED) {
					parent.color = ColorEnum.BLACK;
					uncle.color = ColorEnum.BLACK;
					grandParent.color = ColorEnum.RED;
					// grandparent was recolored to red, so in next iteration we
					// check if it does not break Red-Black property
					currentNode = grandParent;
				}
				// case 2/3 uncle is black - then we perform rotations
				else {
					if (currentNode == parent.right) { // case 2, first rotate left
						currentNode = parent;
						rotateLeft(currentNode);
						parent = (RedBlackNode) currentNode.parent;
					}
					// do not use parent
					parent.color = ColorEnum.BLACK; // case 3
					grandParent.color = ColorEnum.RED;
					rotateRight(grandParent);
				}
			} else if (parent == grandParent.right) {
				RedBlackNode uncle = (RedBlackNode) grandParent.left;
				// case1 - uncle and parent are both red
				// re color both of them to black
				if (((RedBlackNode) uncle).color == ColorEnum.RED) {
					parent.color = ColorEnum.BLACK;
					uncle.color = ColorEnum.BLACK;
					grandParent.color = ColorEnum.RED;
					// grandparent was recolored to red, so in next iteration we
					// check if it does not break Red-Black property
					currentNode = grandParent;
				}
				// case 2/3 uncle is black - then we perform rotations
				else {
					if (currentNode == parent.left) { // case 2, first rotate right
						currentNode = parent;
						rotateRight(currentNode);
						parent = (RedBlackNode) currentNode.parent;
					}
					// do not use parent
					parent.color = ColorEnum.BLACK; // case 3
					grandParent.color = ColorEnum.RED;
					rotateLeft(grandParent);
				}
			}

		}
		// ensure root is black in case it was colored red in fixup
		((RedBlackNode) root).color = ColorEnum.BLACK;
	}

	protected static class RedBlackNode extends Node {
		public ColorEnum color;

		public RedBlackNode(Integer value, Node parent, Node left, Node right, ColorEnum color) {
			super(value, parent, left, right);
			this.color = color;
		}
	}

}
/////////////////////////////////RedBlackTree//////////////////////

/////////////////////////////////Splay tree//////////////////////
class SplayTree extends AbstractSelfBalancingBinarySearchTree {

	@Override
	public Node search(int element) {
		Node node = super.search(element);
		if (node != null) {
			splay(node);
		}
		return node;
	}

	@Override
	public Node insert(int element) {
		Node insertNode = super.insert(element);
		splay(insertNode);
		return insertNode;
	}

	@Override
	protected Node createNode(int value, Node parent, Node left, Node right) {
		return new Node(value, parent, left, right);
	}

	protected void splay(Node node) {
		// move node up until its root
		while (node != root) {
			// Zig step
			Node parent = node.parent;
			if (parent.equals(root)) {
				if (node.equals(parent.left)) {
					rotateRight(parent);
				} else if (node.equals(parent.right)) {
					rotateLeft(parent);
				}
				break;
			} else {
				Node grandParent = parent.parent;
				boolean nodeAndParentLeftChildren = node.equals(parent.left) && parent.equals(grandParent.left);
				boolean nodeAndParentRightChildren = node.equals(parent.right) && parent.equals(grandParent.right);
				boolean nodeRightChildParentLeftChild = node.equals(parent.right) && parent.equals(grandParent.left);
				boolean nodeLeftChildParentRightChild = node.equals(parent.left) && parent.equals(grandParent.right);
				// Zig zig step to the right
				if (nodeAndParentLeftChildren) {
					rotateRight(grandParent);
					rotateRight(parent);
				}
				// Zig zig step to the left
				else if (nodeAndParentRightChildren) {
					rotateLeft(grandParent);
					rotateLeft(parent);
				}
				// Zig zag steps
				else if (nodeRightChildParentLeftChild) {
					rotateLeft(parent);
					rotateRight(grandParent);
				} else if (nodeLeftChildParentRightChild) {
					rotateRight(parent);
					rotateLeft(grandParent);
				}
			}
		}
	}

}

/////////////////////////////////Splay tree//////////////////////
/////////////////////////////////treap//////////////////////
class Treap extends AbstractSelfBalancingBinarySearchTree {

	private Random random = new Random(System.currentTimeMillis());

	@Override
	public Node insert(int element) {
		// insert as in normal BST
		TreapNode insertedNode = (TreapNode) super.insert(element);
		// then rebalance by randomized priority
		while (insertedNode != root) {
			TreapNode parent = (TreapNode) insertedNode.parent;
			if (parent.priority < insertedNode.priority) {
				if (insertedNode.equals(parent.left)) {
					rotateRight(parent);
				} else {
					rotateLeft(parent);
				}
			} else {
				break;
			}
		}
		return insertedNode;
	}

	@Override
	protected Node createNode(int value, Node parent, Node left, Node right) {
		return new TreapNode(value, parent, left, right, random.nextInt(10000));
	}

	private Node rotateDown(TreapNode node) {
		Node replaceNode = null;
		while (true) {
			if (node.left != null) {
				boolean leftNodePriorityLarger = node.right == null
						|| ((TreapNode) node.left).priority >= ((TreapNode) node.right).priority;
				if (leftNodePriorityLarger) {
					Node replace = rotateRight(node);
					if (replaceNode == null) {
						replaceNode = replace;
					}
				} else {
					Node replace = rotateLeft(node);
					if (replaceNode == null) {
						replaceNode = replace;
					}
				}
			} else if (node.right != null) {
				Node replace = rotateLeft(node);
				if (replaceNode == null) {
					replaceNode = replace;
				}
			} else {
				break;
			}
		}
		return replaceNode;
	}

	protected static class TreapNode extends Node {
		public int priority;

		public TreapNode(int value, Node parent, Node left, Node right, int priority) {
			super(value, parent, left, right);
			this.priority = priority;
		}
	}
}

/////////////////////////////////treap tree//////////////////////
/////////////////////////main ///////////////////////////////////
public class test {
	public static void main(String[] args) {
	    int [] SIZE = {1000, 5000, 10000, 50000, 100000};//테스트할 데이터의 수를 넣은 배열
		for (int q = 0; q < 5; q++) {

			AVLTree tree = new AVLTree();// AVL 트리 객체 생성
			BinaryTree BT = new BinaryTree();// BST 객체 생성
			RedBlackTree RBT = new RedBlackTree();// RedBlackTree객체생성
			SplayTree ST = new SplayTree();// SplayTree생성
			Treap treap = new Treap();// Treap 생성

			int AVLNum = 0, BSTNum = 0, RBTNum = 0, STNum = 0, TreapNum = 0;
			int findN = 0;
			int a[] = new int[SIZE[q]];// 랜덤정수들을 임시보관할 배열

			Random z = new Random();

			for (int x = 0; x < SIZE[q]; x++) {// 중복제거
				a[x] = z.nextInt(SIZE[q]+10000) + 1;//랜덤으로 나올수있는 최대수는 배열보다 크게 설정
				for (int y = 0; y < x; y++) {
					if (a[x] == a[y]) {
						x--;
					}
				}
			}
			for (int m = 0; m < SIZE[q]; m++) {
				tree.root = tree.insert(tree.root, a[m]);// avl트리 인서트
				BT.root = BT.addRecursive(BT.root, a[m]);// bst 인서트
				RBT.insert(a[m]); // red black tree 인서트
				ST.insert(a[m]);// splay tree 인서트
				treap.insert(a[m]);// treaps 인서트
			}

			// }
			// 100번 search
			for (int ik = 0; ik < 100; ik++) {
				findN = (int) (Math.random() * SIZE[q]);// 검색할 인덱스 랜덤설정

				AVLNum = AVLNum + tree.findComparsionNum(a[findN]);

				BSTNum = BSTNum + BT.findComparsionNum(a[findN]);

				RBTNum = RBTNum + RBT.findComparsionNum(a[findN]);

				STNum = STNum + ST.findComparsionNum(a[findN]);

				TreapNum = TreapNum + treap.findComparsionNum(a[findN]);
			}

			System.out.println("데이터 양 "+SIZE[q]+"개");

			System.out.println("BST 평균 비교횟수" + (float) BSTNum / 100);

			System.out.println("AVL 평균 비교횟수" + (float) AVLNum / 100);

			System.out.println("RedBlackTree 평균 비교횟수" + (float) RBTNum / 100);

			System.out.println("splayTree 평균 비교횟수" + (float) STNum / 100);

			System.out.println("TreapTree 평균 비교횟수" + (float) TreapNum / 100);
		}

	}
}