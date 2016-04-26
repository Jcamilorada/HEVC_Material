/**
 * @author Juan Rada
 */
package reader


class FileReader
{
    static final VIDEOS = ["PartyScene", "ParkRun", "Kimono1", "BQMall", "City", "PartyScene", "Traffic"]
    static final Resoulutions = ["intra", "ip_8", "ip_16", "ip_32", "ip_64"]

    static ProcessInfo readFile(String videoName, String resolution)
    {
        String fileName = '../../HEVC_TEST/' + videoName + '/' + videoName + '_frames128_' + resolution + '.out'
        File file = new File(fileName)
        String[] lines = file.readLines()

        def iInfo = lines[203].replaceAll("\\s+", " ").split(" ")
        def bInfo = lines[213].replaceAll("\\s+", " ").split(" ")
        def info = lines[198].replaceAll("\\s+", " ").split(" ")

        String i_frames = iInfo[1]
        String i_bitrate = iInfo[3]
        String i_yuv_psnr = iInfo[7]

        String b_frames = bInfo[1]
        String b_bitrate = bInfo[3]
        String b_yuvPSNR = bInfo[7]

        String totalFrames = info[0]
        String bitrate = info[3]
        String psnr = info[7]

        String totalTime = lines[218].split(" ")[6]

        new ProcessInfo(
            video: videoName,
            i_frames: i_frames,
            i_bitrate: i_bitrate,
            i_yuv_psnr: i_yuv_psnr,
            b_frames: b_frames,
            b_bitrate: b_bitrate,
            b_yuvPSNR: b_yuvPSNR,
            totalTime: totalTime,
            totalFrames: totalFrames,
            bitrate: bitrate,
            psnr :psnr
        )
    }

    static void main(String[] args)
    {
        def processData = []
        VIDEOS.each { video ->
            Resoulutions.each { res ->
                processData.add(readFile(video, res))
            }
        }

        File resultFile = new File("result.txt")
        PrintWriter writer = new PrintWriter(resultFile)
        writer.println("Video;Intra-Frames;Intra-Bitrate;Intra-PSNR;B-Frames;B-Bitrate;B-PSNR;Total-Time;Frames;Bitrate;PSNR")

        processData.each {line ->
            writer.println(line.toString())
        }
        writer.close()

    }
}

class ProcessInfo
{
    String video
    String i_frames
    String i_bitrate
    String i_yuv_psnr
    String b_frames
    String b_bitrate
    String b_yuvPSNR
    String totalTime
    String totalFrames
    String bitrate
    String psnr


    @Override
    String toString() {
        video + ';' + i_frames + ';' + i_bitrate + ';' + i_yuv_psnr + ';' + b_frames + ';' + b_bitrate + ';'  +
                b_yuvPSNR + ';'+ totalTime + ';' + totalFrames + ';' + bitrate + ';' + psnr
    }
}


