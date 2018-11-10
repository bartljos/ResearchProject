package DemoComponents;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class Display extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private String currentText = "";
	
	@Override
	public void paintComponent(Graphics g)
	{
		
		g.setColor(Color.red);
		g.drawString(this.currentText, 20, 20);
	}
	
	public void setCurrentText(String text)
	{
		this.currentText = text;
		this.repaint();
	}
	
	public String getCurrentText()
	{
		return this.currentText;
	}
	
	
	
}
