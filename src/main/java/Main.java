import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String caminhoArquivoEntrada = "src/main/resources/input/Resultado.txt";
        String caminhoArquivoSaida = "src/main/resources/output/final.xml";
        processarArquivo(caminhoArquivoEntrada, caminhoArquivoSaida);
        System.out.println("Processamento concluído. Verifique o arquivo final.xml.");
    }

    public static void processarArquivo(String caminhoArquivoEntrada, String caminhoArquivoSaida) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(caminhoArquivoEntrada));
             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(caminhoArquivoSaida))) {

            bufferedWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            bufferedWriter.write("<registros>\n");

            String id = "";
            String titulo = "";
            String ecid = "";
            String usuario = "";

            String linha;
            while ((linha = bufferedReader.readLine()) != null) {
                if (linha.contains("<ns7:Tarefa ID")) {
                    id = extrairValorTag(linha);
                } else if (linha.contains("<ns7:Titulo>")) {
                    titulo = extrairValorTag(linha);
                } else if (linha.contains("<ns7:Ecid>")) {
                    ecid = extrairValorTag(linha);
                } else if (linha.contains("<ns7:Nome>")) {
                    usuario = extrairValorTag(linha);
                } else if (linha.contains("fim")) {
                    // if (!id.isEmpty() && !titulo.isEmpty() && !ecid.isEmpty() && !usuario.isEmpty()) {
                    //String registro = String.format("\t<registro>\n\t\t<ID>%s</ID>\n\t\t<Titulo>%s</Titulo>\n\t\t<Ecid>%s</Ecid>\n\t\t<Usuario>%s</Usuario>\n\t</registro>\n", id, titulo, ecid, usuario);
                    String registro = String.format("\t<registro>\n\t\t<Titulo>%s</Titulo>\n\t\t<Ecid>%s</Ecid>\n\t\t<Usuario>%s</Usuario>\n\t</registro>\n", titulo, ecid, usuario);
                    bufferedWriter.write(registro);
                    // }
                    id = titulo = ecid = usuario = "";
                    System.out.println("Chegou aqui");
                }
            }

            bufferedWriter.write("</registros>\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String extrairValorTag(String linha) {
        int inicio = linha.indexOf(">") + 1;
        int fim = linha.indexOf("<", inicio);
        return (fim != -1) ? linha.substring(inicio, fim).trim() : "";
    }
}
