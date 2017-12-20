import java.util.Iterator;
import components.tree.Tree;
import components.tree.Tree1;
import components.sequence.Sequence;
import components.sequence.Sequence1L;
import java.util.Comparator;

import components.binarytree.BinaryTree;
import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Utility class with implementation of {@code BinaryTree} static, generic
 * methods height and isInTree.
 *
 * @author Put your name here
 *
 */
public final class BinaryTreeMethods {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private BinaryTreeMethods() {
    }
    
    /**
     * Returns the size of the given {@code Tree<T>}.
     * 
     * @param <T>
     *            the type of the {@code Tree} node labels
     * @param t
     *            the {@code Tree} whose size to return
     * @return the size of the given {@code Tree}
     * @ensures size = |t|
     */
    public static <T> int size(Tree<T> t) {
    		int size = 1;
    		if (t.numberOfSubtrees() > 0) {
    			Sequence<Tree<T>> subtrees = new Sequence1L<Tree<T>>();
    			T root = t.disassemble(subtrees);
    			for (Tree<T> sub: subtrees) {
    				size += size(sub);
    			}
    			t.assemble(root, subtrees);
    		}
    		return size;
    }
    
    /**
     * Returns the size of the given {@code Tree<T>}.
     * 
     * @param <T>
     *            the type of the {@code Tree} node labels
     * @param t
     *            the {@code Tree} whose size to return
     * @return the size of the given {@code Tree}
     * @ensures size = |t|
     */
    public static <T> int sizeIt(Tree<T> t) {
    		int size = 0;
    		for (Iterator<T> iterator = t.iterator(); iterator.hasNext();) {
			size++;
		}
    		return size;
    }
    
    /**
     * Returns the height of the given {@code Tree<T>}.
     * 
     * @param <T>
     *            the type of the {@code Tree} node labels
     * @param t
     *            the {@code Tree} whose height to return
     * @return the height of the given {@code Tree}
     * @ensures height = ht(t)
     */
    public static <T> int height(Tree<T> t) {
    		int height = 0;
    		if (t.size() > 0) {
    			height++;
    			Sequence<Tree<T>> subtrees = new Sequence1L<Tree<T>>();
    			T root = t.disassemble(subtrees);
    			int largestSubtreeHeight = 0;
    			for (Tree<T> sub: subtrees) {
    				int subHeight = height(sub);
    				if (subHeight > largestSubtreeHeight) {
    					largestSubtreeHeight = subHeight;
    				}
    			}
    			height += largestSubtreeHeight;
    			t.assemble(root, subtrees);
    		}
    		return height;
    }
    
    /**
     * Returns the largest integer in the given {@code Tree<Integer>}.
     * 
     * @param t
     *            the {@code Tree<Integer>} whose largest integer to return
     * @return the largest integer in the given {@code Tree<Integer>}
     * @requires |t| > 0
     * @ensures <pre>
     * max is in labels(t)  and
     * for all i: integer where (i is in labels(t)) (i <= max)
     * </pre>
     */
    public static int max(Tree<Integer> t) {
    		int max = 0;
    		if (t.size() > 0) {
    			Sequence<Tree<Integer>> subtrees = new Sequence1L<Tree<Integer>>();
    			int root = t.disassemble(subtrees);
    			max = root;
    			int maxOfSubtrees = 0;
    			for (Tree<Integer> sub: subtrees) {
    				int subMax = max(sub);
    				if (subMax > maxOfSubtrees) {
    					maxOfSubtrees = subMax;
    				}
    			}
    			t.assemble(root, subtrees);
    		}
    		return max;
    }

    /**
     * Returns the size of the given {@code BinaryTree<T>}.
     *
     * @param <T>
     *            the type of the {@code BinaryTree} node labels
     * @param t
     *            the {@code BinaryTree} whose size to return
     * @return the size of the given {@code BinaryTree}
     * @ensures size = |t|
     */
    public static <T> int size(BinaryTree<T> t) {
        int sum = 0;
        BinaryTree<T> emptyTree = t.newInstance();
        if (!t.equals(emptyTree)) {
            BinaryTree<T> left = t.newInstance();
            BinaryTree<T> right = t.newInstance();
            T root = t.disassemble(left, right);
            sum += size(left) + size(right);
            t.assemble(root, left, right);
        }
        return sum;
    }

    /**
     * Returns the size of the given {@code BinaryTree<T>}.
     *
     * @param <T>
     *            the type of the {@code BinaryTree} node labels
     * @param t
     *            the {@code BinaryTree} whose size to return
     * @return the size of the given {@code BinaryTree}
     * @ensures size = |t|
     */
    public static <T> int sizeIterative(BinaryTree<T> t) {
        int size = 0;
        for (Iterator<T> iterator = t.iterator(); iterator.hasNext();) {
			size++;
		}
        return size;
    }

    /*
     * ADDITIONAL ACTIVITY
     */

    /**
     * Returns the height of the given {@code BinaryTree<T>}.
     *
     * @param <T>
     *            the type of the {@code BinaryTree} node labels
     * @param t
     *            the {@code BinaryTree} whose height to return
     * @return the height of the given {@code BinaryTree}
     * @ensures height = ht(t)
     */
    public static <T> int height(BinaryTree<T> t) {
        assert t != null : "Violation of: t is not null";

        int height = 0;
        if (t.size() > 0) {
            height++;
            BinaryTree<T> left = t.newInstance();
            BinaryTree<T> right = t.newInstance();
            T root = t.disassemble(left, right);
            int leftH = height(left);
            int rightH = height(right);
            if (leftH > rightH) {
                height += leftH;
            } else {
                height += rightH;
            }
            t.assemble(root, left, right);
        }
        return height;
    }

    /**
     * Returns true if the given {@code T} is in the given {@code BinaryTree<T>}
     * or false otherwise.
     *
     * @param <T>
     *            the type of the {@code BinaryTree} node labels
     * @param t
     *            the {@code BinaryTree} to search
     * @param x
     *            the {@code T} to search for
     * @return true if the given {@code T} is in the given {@code BinaryTree},
     *         false otherwise
     * @ensures isInTree = [true if x is in t, false otherwise]
     */
    public static <T> boolean isInTree(BinaryTree<T> t, T x) {
        assert t != null : "Violation of: t is not null";
        assert x != null : "Violation of: x is not null";

        //        boolean isInTree = false;
        //        if (t.size() > 0) {
        //            BinaryTree<T> left = t.newInstance();
        //            BinaryTree<T> right = t.newInstance();
        //            T root = t.disassemble(left, right);
        //            isInTree = root.equals(x) || isInTree(left, x) ||
        //            isInTree(right, x);
        //            t.assemble(root, left, right);
        //        }
        //        return isInTree;

        boolean isInTree = false;
        for (T root : t) {
            if (root.equals(x)) {
                isInTree = true;
            }
        }
        return isInTree;
    }
    


    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        out.print("Input a tree (or just press Enter to terminate): ");
        String str = in.nextLine();
        while (str.length() > 0) {
            BinaryTree<String> t = BinaryTreeUtility.treeFromString(str);
            out.println("Tree = " + BinaryTreeUtility.treeToString(t));
            out.println("Height = " + height(t));
            out.print("  Input a label to search "
                    + "(or just press Enter to input a new tree): ");
            String label = in.nextLine();
            while (label.length() > 0) {
                if (isInTree(t, label)) {
                    out.println("    \"" + label + "\" is in the tree");
                } else {
                    out.println("    \"" + label + "\" is not in the tree");
                }
                out.print("  Input a label to search "
                        + "(or just press Enter to input a new tree): ");
                label = in.nextLine();
            }
            out.println();
            out.print("Input a tree (or just press Enter to terminate): ");
            str = in.nextLine();
        }

        in.close();
        out.close();
    }

}
