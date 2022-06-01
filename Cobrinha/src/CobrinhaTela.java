import javax.swing.JFrame;

public class CobrinhaTela extends JFrame{
	private static final long serialVersionUID = 1L;

	public CobrinhaTela() {
		
		this.add(new CobrinhaPainel()); 
		this.setTitle("Snake");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
}
