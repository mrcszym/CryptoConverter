import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class CryptoConvGUI extends JFrame{

    private JPanel mainPanel;
    private JTextField passValueTextField;
    private JLabel passValueLabel;
    private JButton convertBtn;
    private JLabel resultLabel;

    public CryptoConvGUI(String title) {
        super(title);

        Image icon = Toolkit.getDefaultToolkit().getImage("images/logo.png");
        this.setIconImage(icon);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.setResizable(true);
        this.pack();

        convertBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                resultLabel.setText(parseAndCalculateResult(fetchBtcValue(), passValueTextField.getText()) + " BTC");
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new CryptoConvGUI("Crypto Currency Converter");
        frame.setBounds(500,500,500,500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    static String fetchBtcValue() {
        try {
            Document document = Jsoup.
                    connect("https://www.google.com/search?q=1+Bitcoin+to+PLN&client=opera-gx&hs=Wq9&sxsrf=APq-WBvyQOspTN1J1KV9PvwhSrNMEf-Law%3A1645201187665&ei=I8cPYs6BKIrfrgTy7YGICg&oq=1+btc&gs_lcp=Cgdnd3Mtd2l6EAEYAjIHCCMQsAMQJzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzISCC4QxwEQ0QMQyAMQsAMQQxgAMhIILhDHARDRAxDIAxCwAxBDGABKBAhBGABKBAhGGABQAFgAYPEQaAJwAXgAgAEAiAEAkgEAmAEAyAELwAEB2gEECAAYCA&sclient=gws-wiz").get();
            Elements elements = document.getElementsByClass("pclqee");

            return elements.get(0).text();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    double parseAndCalculateResult(String fetchedValue, String passedValue) {
        String fetchedValueComaModified = fetchedValue.replace(',', '.');
        String finalFetchedValue = fetchedValueComaModified.replaceAll("\\s+",""); //no spaces

        String passedValueComaModified = passedValue.replace(',', '.');
        String finalPassedValue = passedValueComaModified.replaceAll("\\s+",""); //no spaces

        return Double.parseDouble(finalPassedValue) / Double.parseDouble(finalFetchedValue);
    }
}
