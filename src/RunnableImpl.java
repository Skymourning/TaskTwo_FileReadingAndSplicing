import java.io.*;

public class RunnableImpl implements Runnable{
    private static int now = 0;
    static File file = new File("oldFile.txt");
    static RandomAccessFile view = null;
    final static int len = 300;
    public String totalString = "";
    private File newFile;
    private BufferedWriter bw;
    private Boolean flag = true;
    RunnableImpl() throws IOException {
        view = new RandomAccessFile(file, "rw");
    }
    public void run() {
        while(true){
            try {
                synchronized (view) {        //synchronized实现多线程的同步
                    byte[] b = new byte[len];   //将文件内容读取到b字节数组中
                    view.seek(now);
                    int temp = view.read(b);
                    if(temp == -1)
                    {
                        return ;
                    }
                    now += temp;
                    System.out.println(new String(b));
                    totalString = new String(b);
                    if(!flag)   //如标记前面的文件不存在，则return
                    {
                        flag = true;  //改回原来的文件标记符
                        return;
                    }
                    try
                    {
                        newFile = new File("newFile.txt");
                        if(newFile.exists())  //存在，不用创建新文件
                        {
                            System.out.println("文件存在，可以写入!");
                        }
                        else //不存在，创建新文件
                        {
                            System.out.println("文件不存在，准备创建新文件");
                            newFile.createNewFile();
                            System.out.println("文件创建成功，可以写入");
                        }
                        bw = new BufferedWriter(new FileWriter(newFile,true));

                        bw.write(totalString,0,totalString.length());
                        bw.flush();
                        System.out.println("写入完成");
                        totalString="\r\t";   //清空原来的字符串
                    }
                    catch(FileNotFoundException e)
                    {
                        System.out.println(e);
                    }
                    catch(IOException e)
                    {
                        System.out.println(e);
                    }
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}