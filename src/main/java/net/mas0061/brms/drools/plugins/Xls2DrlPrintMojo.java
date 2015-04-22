package net.mas0061.brms.drools.plugins;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.drools.decisiontable.InputType;
import org.drools.decisiontable.SpreadsheetCompiler;

/**
 * Xls2DrlPrintMojo is class of Maven plugin.
 * This is plugin for changing the decision table of Excel into DRL and displaying it.
 *
 * @author mas0061
 * @version 1.0.0
 */
@Mojo(name = "print", threadSafe = true)
@Execute(goal = "print", phase = LifecyclePhase.PROCESS_RESOURCES)
public class Xls2DrlPrintMojo extends AbstractMojo {
	/**
	 * When this parameter is established,
	 * it's possible to change the directory in which a decision table of a target exists.
	 */
	@Parameter(defaultValue = "src/main/resources", required = true)
	private File resourceDirectory;

	private static final String XLS_MIME_TYPE = "application/vnd.ms-excel";

	/**
	 * Plugin main method
	 * @throws MojoExecutionException some error...
	 */
	@Override
	public void execute() throws MojoExecutionException {
		Path path = resourceDirectory.toPath();
		
		try {
			Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path file,  BasicFileAttributes attrs) {
					try(InputStream stream = Files.newInputStream(file)) {
						if (XLS_MIME_TYPE.equals(Files.probeContentType(file))) {
							getLog().info("###### File : " + file.toString() + " ######");
							
							SpreadsheetCompiler compiler = new SpreadsheetCompiler();
							getLog().info(compiler.compile(stream, InputType.XLS));
						}
					} catch (IOException e) {
						getLog().error("Error IOException", e);
					}
					return FileVisitResult.CONTINUE;
				}
			});
		} catch (IOException e) {
			throw new MojoExecutionException("Error IOException", e);
		}
	}
}
