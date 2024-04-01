package br.eti.kge.ImageExample.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exemplo de API com retorno de imagem com base em um arquivo gravado em
 * resources/images
 *
 * @author kge
 */
@RestController
public class ImageController {

    @GetMapping("/")
    public @ResponseBody
    String getText() {

        return "API Funcionando!!!";

    }

    // Retorna apenas os bytes da imagem. Navegador não sabe o que fazer com 
    // os bytes e apresenta os caracteres do arquivo.
    @GetMapping("/image-raw")
    public @ResponseBody
    byte[] getImageRaw() throws IOException {
        InputStream in = getClass().getResourceAsStream("/images/captura1.png");
        return IOUtils.toByteArray(in);
    }

    // Em substituição ao endpoint acima, podemos informar o parametro "produces"
    // apontando para um IMAGE_PNG_VALUE. A partir deste momento o navegador consegue exibir a imagem.
    // Dá a instrução para o navegador que é um media-type PNG
    @GetMapping(
            value = "/image-media-type",
            produces = MediaType.IMAGE_PNG_VALUE
    )
    public @ResponseBody
    byte[] getImageMediaType() throws IOException {
        InputStream in = getClass().getResourceAsStream("/images/captura1.png");
        return IOUtils.toByteArray(in);
    }

    // o endpoint abaixo retorna no formato RAW um arquivo (mesmo que no exemplo do 
    // image-raw), porém com a opção "produces".
    @GetMapping(
            value = "/get-file",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public @ResponseBody
    byte[] getFile() throws IOException {
        InputStream in = getClass().getResourceAsStream("/meusArquivos/Sample_File.txt");
        return IOUtils.toByteArray(in);
    }


    // o endpoint abaixo faz o mesmo do endpoint acima, podém como um ByteArrayResource em vez
    // de um ByteArray;
    @GetMapping(
            value = "/get-file-viabytearrayresource",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public @ResponseBody
    Resource getFileViaByteArrayResource() throws IOException, URISyntaxException {
        Path path = Paths.get(getClass().getResource("/meusArquivos/Sample_File.txt").toURI());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
        return resource;
    }

}
