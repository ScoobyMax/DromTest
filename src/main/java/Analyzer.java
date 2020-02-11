import java.io.*;
import org.apache.commons.cli.*;


public class Analyzer {

    public static void main(String[] args) {
        float maxTime, availability;

        Options options = new Options();
        options.addOption(Option.builder("t").hasArg().argName("ms")
                              .desc("max response time").required().build());
        options.addOption(Option.builder("u").hasArg().argName("N")
                              .desc("percentage of successful responses").required().build());

        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(options, args);
            maxTime = Float.parseFloat(cmd.getOptionValue('t'));
            availability = Float.parseFloat(cmd.getOptionValue('u'));
            if (maxTime <= 0 || availability < 0 || availability > 100) {
                throw new IllegalArgumentException();
            }


            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
            LogScanner analyzer = new LogScanner(stdin, maxTime, availability);
            analyzer.parse();
        } catch (ParseException | IllegalArgumentException e) {
            System.out.println("Wrong args.");
            new HelpFormatter().printHelp("java -jar drom.jar", options);
            System.exit(2);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

}