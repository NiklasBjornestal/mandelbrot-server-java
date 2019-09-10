import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/")
public class MandelbrotService {

    /**
     * Add function for route
     * /mandelbrot/{min_c_re}/{min_c_im}/{max_c_re}/{max_c_im}/{x}/{y}/{inf_n}
     *
     * Calculates and returns an array containing a gray scale 8bit mandelbrot fractal image
     *
     * @param min_c_re min real value
     * @param min_c_im min imaginary value
     * @param max_c_re max real value
     * @param max_c_im max imaginary value
     * @param inf_n Max number of iterations to calculate
     * @param x_size x size in pixels
     * @param y_size y size in pixels
     * @param uriInfo info about current uri
     * @return response containing calculated mandelbrot image (byte array)
     */
    @GET
    @Path("mandelbrot/{min_c_re}/{min_c_im}/{max_c_re}/{max_c_im}/{x}/{y}/{inf_n}")
    public Response getSubImage(
            @PathParam("min_c_re") double min_c_re,
            @PathParam("min_c_im") double min_c_im,
            @PathParam("max_c_re") double max_c_re,
            @PathParam("max_c_im") double max_c_im,
            @PathParam("x") int x_size,
            @PathParam("y") int y_size,
            @PathParam("inf_n") int inf_n,
            @Context UriInfo uriInfo) {
        System.out.println("Serving: "+uriInfo.getAbsolutePath());
        byte[] image = new byte[ x_size * y_size ];
        double zx, zy, cX, cY, tmp;
        for (int y = 0; y < y_size; y++) {
            for (int x = 0; x < x_size; x++) {
                zx = zy = 0;
                cX = x * (max_c_re - min_c_re) / x_size + min_c_re;
                cY = y * (max_c_im - min_c_im) / y_size + min_c_im;
                int iter = inf_n;
                while (zx * zx + zy * zy < 4 && iter > 0) {
                    tmp = zx * zx - zy * zy + cX;
                    zy = 2.0 * zx * zy + cY;
                    zx = tmp;
                    iter--;
                }
                image [x + y*x_size] = (byte)(iter % 256);
            }
        }
        System.out.println("Done");
        return Response.ok(image)
                .build();
    }
}
