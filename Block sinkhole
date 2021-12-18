package il.ac.idc.cs.sinkhole;

import java.io.IOException;
import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
public class SinkholeServer {

    final static int DNSPort = 53; // the port that DNS servers listen to
    final static int defaultSinkholePort = 5300; // the port the sinkhole will listen to

    public static void main(String[] args) {
        // initialing variables
        UDPServer server = null;
        Message currentMessage = null;
        UDPClient client = null;
        Message initialQuery;
        hashBlockList blockList;
        String currentDnsServer;
        int round;

        try {

            // open the UDP server for the sinkhole
            server = new UDPServer(defaultSinkholePort, 1024);

            // loading block list
            if (args.length > 0){
                 blockList = new hashBlockList(args[0]);
            }
            else{
                blockList = null;
            }

            while (true){
                try {

                    // listening to incoming requests
                    byte[] message = server.receive();
                    initialQuery = new Message(message);

                    // handles blocked domain name if necessary
                    if (blockList != null && blockList.isInBlockList(initialQuery)) {
                        currentMessage = initialQuery;

                        // setting flags accordingly
                        currentMessage.headers.setRCODE(3);
                        currentMessage.headers.setZToZero();
                    } else {

                        // setting flags to iterative query
                        initialQuery.headers.setRD(0);
                        initialQuery.headers.setRA(0);

                        // a new client for each query to the sinkhole
                        client = new UDPClient(1024);
                        currentDnsServer = rootServers.getRandomServer();

                        round = 0;
                        while (round < 16) {
                            round++;
                            message = client.sendReceive(initialQuery.getContent(), initialQuery.getEnd(), currentDnsServer, DNSPort);
                            currentMessage = new Message(message);
                            if (currentMessage.headers.getNumAnswers() != 0 ||
                                    currentMessage.headers.getNumAuthorities() == 0 ||
                                    (currentMessage.headers.getRCODE() != 0 && currentMessage.headers.getRCODE() != 3) ||
                                    (currentMessage.headers.getAA() == 1 && currentMessage.headers.getRCODE() == 3)) {
                                break;
                            }
                            currentDnsServer = currentMessage.getAuthorities(0).getRData();
                        }


                    }

                    currentMessage.headers.setAA(0);
                    currentMessage.headers.setRD(1);
                    currentMessage.headers.setQR(1);
                    currentMessage.headers.setRA(1);
                    server.send(currentMessage.getContent(), currentMessage.getEnd());
                } catch (SocketException e){
                    System.err.printf("Client Socket unavailable: " + e.getMessage());
                    System.exit(1);
                }
                finally {
                    if (client != null){
                        client.close();
                    }
                }
            }

        }catch (SocketException e) {
            System.err.println("Server Socket unavailable: " + e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println("IO exception: " + e.getMessage());
            System.exit(1);
        }
        finally {
            if (server != null){
                server.close();
            }

        }

    }

}
