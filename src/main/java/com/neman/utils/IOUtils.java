package com.neman.utils;

/**
 * Created by nemanja on 8/22/14.
 */


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.Selector;


/**
 * Origin of code: apache commons-io
 */
public class IOUtils {

    /**
     * Instances should NOT be constructed in standard programming.
     */
    public IOUtils() {
        super();
    }

    //-----------------------------------------------------------------------


    /**
     * Closes an <code>Reader</code> unconditionally.
     * <p/>
     * Equivalent to {@link Reader#close()}, except any exceptions will be ignored.
     * This is typically used in finally blocks.
     * <p/>
     * Example code:
     * <pre>
     *   char[] main.java.com.neman.data = new char[1024];
     *   Reader in = null;
     *   try {
     *       in = new FileReader("foo.txt");
     *       in.read(main.java.com.neman.data);
     *       in.close(); //close errors are handled
     *   } catch (Exception e) {
     *       // error handling
     *   } finally {
     *       IOUtils.closeQuietly(in);
     *   }
     * </pre>
     *
     * @param input the Reader to close, may be null or already closed
     */
    public static void closeQuietly(final Reader input) {
        closeQuietly((Closeable) input);
    }

    /**
     * Closes an <code>Writer</code> unconditionally.
     * <p/>
     * Equivalent to {@link Writer#close()}, except any exceptions will be ignored.
     * This is typically used in finally blocks.
     * <p/>
     * Example code:
     * <pre>
     *   Writer out = null;
     *   try {
     *       out = new StringWriter();
     *       out.write("Hello World");
     *       out.close(); //close errors are handled
     *   } catch (Exception e) {
     *       // error handling
     *   } finally {
     *       IOUtils.closeQuietly(out);
     *   }
     * </pre>
     *
     * @param output the Writer to close, may be null or already closed
     */
    public static void closeQuietly(final Writer output) {
        closeQuietly((Closeable) output);
    }

    /**
     * Closes an <code>InputStream</code> unconditionally.
     * <p/>
     * Equivalent to {@link InputStream#close()}, except any exceptions will be ignored.
     * This is typically used in finally blocks.
     * <p/>
     * Example code:
     * <pre>
     *   byte[] main.java.com.neman.data = new byte[1024];
     *   InputStream in = null;
     *   try {
     *       in = new FileInputStream("foo.txt");
     *       in.read(main.java.com.neman.data);
     *       in.close(); //close errors are handled
     *   } catch (Exception e) {
     *       // error handling
     *   } finally {
     *       IOUtils.closeQuietly(in);
     *   }
     * </pre>
     *
     * @param input the InputStream to close, may be null or already closed
     */
    public static void closeQuietly(final InputStream input) {
        closeQuietly((Closeable) input);
    }

    /**
     * Closes an <code>OutputStream</code> unconditionally.
     * <p/>
     * Equivalent to {@link OutputStream#close()}, except any exceptions will be ignored.
     * This is typically used in finally blocks.
     * <p/>
     * Example code:
     * <pre>
     * byte[] main.java.com.neman.data = "Hello, World".getBytes();
     *
     * OutputStream out = null;
     * try {
     *     out = new FileOutputStream("foo.txt");
     *     out.write(main.java.com.neman.data);
     *     out.close(); //close errors are handled
     * } catch (IOException e) {
     *     // error handling
     * } finally {
     *     IOUtils.closeQuietly(out);
     * }
     * </pre>
     *
     * @param output the OutputStream to close, may be null or already closed
     */
    public static void closeQuietly(final OutputStream output) {
        closeQuietly((Closeable) output);
    }

    /**
     * Closes a <code>Closeable</code> unconditionally.
     * <p/>
     * Equivalent to {@link Closeable#close()}, except any exceptions will be ignored. This is typically used in
     * finally blocks.
     * <p/>
     * Example code:
     * <p/>
     * <pre>
     * Closeable closeable = null;
     * try {
     *     closeable = new FileReader(&quot;foo.txt&quot;);
     *     // process closeable
     *     closeable.close();
     * } catch (Exception e) {
     *     // error handling
     * } finally {
     *     IOUtils.closeQuietly(closeable);
     * }
     * </pre>
     * <p/>
     * Closing all streams:
     * <p/>
     * <pre>
     * try {
     *     return IOUtils.copy(inputStream, outputStream);
     * } finally {
     *     IOUtils.closeQuietly(inputStream);
     *     IOUtils.closeQuietly(outputStream);
     * }
     * </pre>
     *
     * @param closeable the objects to close, may be null or already closed
     * @since 2.0
     */
    public static void closeQuietly(final Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (final IOException ioe) {
            // ignore
        }
    }

    /**
     * Closes a <code>Closeable</code> unconditionally.
     * <p/>
     * Equivalent to {@link Closeable#close()}, except any exceptions will be ignored.
     * <p/>
     * This is typically used in finally blocks to ensure that the closeable is closed
     * even if an Exception was thrown before the normal close statement was reached.
     * <br>
     * <b>It should not be used to replace the close statement(s)
     * which should be present for the non-exceptional case.</b>
     * <br>
     * It is only intended to simplify tidying up where normal processing has already failed
     * and reporting close failure as well is not necessary or useful.
     * <p/>
     * Example code:
     * <p/>
     * <pre>
     * Closeable closeable = null;
     * try {
     *     closeable = new FileReader(&quot;foo.txt&quot;);
     *     // processing using the closeable; may throw an Exception
     *     closeable.close(); // Normal close - exceptions not ignored
     * } catch (Exception e) {
     *     // error handling
     * } finally {
     *     <b>IOUtils.closeQuietly(closeable); // In case normal close was skipped due to Exception</b>
     * }
     * </pre>
     * <p/>
     * Closing all streams:
     * <br>TODO - fix this example, it is wrong; ought not to ignore Exceptions here
     * <pre>
     * try {
     *     return IOUtils.copy(inputStream, outputStream);
     * } finally {
     *     IOUtils.closeQuietly(inputStream, outputStream);
     * }
     * </pre>
     *
     * @param closeables the objects to close, may be null or already closed
     * @see #closeQuietly(Closeable)
     * @since 2.5
     */
    public static void closeQuietly(final Closeable... closeables) {
        if (closeables == null) {
            return;
        }
        for (final Closeable closeable : closeables) {
            closeQuietly(closeable);
        }
    }

    /**
     * Closes a <code>Socket</code> unconditionally.
     * <p/>
     * Equivalent to {@link Socket#close()}, except any exceptions will be ignored.
     * This is typically used in finally blocks.
     * <p/>
     * Example code:
     * <pre>
     *   Socket socket = null;
     *   try {
     *       socket = new Socket("http://www.foo.com/", 80);
     *       // process socket
     *       socket.close();
     *   } catch (Exception e) {
     *       // error handling
     *   } finally {
     *       IOUtils.closeQuietly(socket);
     *   }
     * </pre>
     *
     * @param sock the Socket to close, may be null or already closed
     * @since 2.0
     */
    public static void closeQuietly(final Socket sock) {
        if (sock != null) {
            try {
                sock.close();
            } catch (final IOException ioe) {
                // ignored
            }
        }
    }

    /**
     * Closes a <code>Selector</code> unconditionally.
     * <p/>
     * Equivalent to {@link Selector#close()}, except any exceptions will be ignored.
     * This is typically used in finally blocks.
     * <p/>
     * Example code:
     * <pre>
     *   Selector selector = null;
     *   try {
     *       selector = Selector.open();
     *       // process socket
     *
     *   } catch (Exception e) {
     *       // error handling
     *   } finally {
     *       IOUtils.closeQuietly(selector);
     *   }
     * </pre>
     *
     * @param selector the Selector to close, may be null or already closed
     * @since 2.2
     */
    public static void closeQuietly(final Selector selector) {
        if (selector != null) {
            try {
                selector.close();
            } catch (final IOException ioe) {
                // ignored
            }
        }
    }

    /**
     * Closes a <code>ServerSocket</code> unconditionally.
     * <p/>
     * Equivalent to {@link ServerSocket#close()}, except any exceptions will be ignored.
     * This is typically used in finally blocks.
     * <p/>
     * Example code:
     * <pre>
     *   ServerSocket socket = null;
     *   try {
     *       socket = new ServerSocket();
     *       // process socket
     *       socket.close();
     *   } catch (Exception e) {
     *       // error handling
     *   } finally {
     *       IOUtils.closeQuietly(socket);
     *   }
     * </pre>
     *
     * @param sock the ServerSocket to close, may be null or already closed
     * @since 2.2
     */
    public static void closeQuietly(final ServerSocket sock) {
        if (sock != null) {
            try {
                sock.close();
            } catch (final IOException ioe) {
                // ignored
            }
        }
    }


}
