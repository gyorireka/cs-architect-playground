using System.Net.WebSockets;
using System.Text;
using TelescopeService.Models;

namespace TelescopeService.Generator
{
    //This whole class is ugly and meant for testing purposes only
    public static class Generator
    {
        public const string InputTextTemplate = "s";
        public const string StableDiffusionHost = "http://localhost:7860/file=";

        public async static Task<GeneratedImage> Generate(string color)
        {
            string response = await Generator.GenerateImage(color);
            string downloadUrlFromGenerator = Generator.GetDownloadURL(response);

            var lastIndexOfSlash = downloadUrlFromGenerator.LastIndexOf("/");
            var imageName = downloadUrlFromGenerator.Substring(lastIndexOfSlash + 1, downloadUrlFromGenerator.Length - lastIndexOfSlash - 1);

            using (var client = new HttpClient())
            {
                byte[] dataBytes = await client.GetByteArrayAsync(downloadUrlFromGenerator);

                return new GeneratedImage(imageName, dataBytes);
            }
        }

        //Requests image generation from StableDiffusion webUI via socket
        private async static Task<string> GenerateImage(string color)
        {
            var ws = new ClientWebSocket();
            await ws.ConnectAsync(new Uri("ws://localhost:7860/queue/join"), CancellationToken.None);
            var end = "";

            var receiveTask = Task.Run(async () => {
                var buffer = new byte[1024];

                while (ws.State == WebSocketState.Open)
                {
                    var result = await ws.ReceiveAsync(new ArraySegment<byte>(buffer), CancellationToken.None);
                    var message = Encoding.UTF8.GetString(buffer, 0, result.Count);

                    var sendMessage = "{\"fn_index\":96,\"session_hash\":\"jn0alhfxvz\"}";
                    var sendBuffer = new ArraySegment<byte>(Encoding.UTF8.GetBytes(sendMessage));
                    await ws.SendAsync(sendBuffer, WebSocketMessageType.Text, true, CancellationToken.None);

                    result = await ws.ReceiveAsync(new ArraySegment<byte>(buffer), CancellationToken.None);
                    message = Encoding.UTF8.GetString(buffer, 0, result.Count);

                    result = await ws.ReceiveAsync(new ArraySegment<byte>(buffer), CancellationToken.None);
                    message = Encoding.UTF8.GetString(buffer, 0, result.Count);

                    var sendData = "{\"data\":[\"task(nnoa5pp81p74r3q)\",\"a "+ color + " star dark atmosphere watched from space\",\"\",[],20,\"Euler a\",1,1,7,512,512,false,0.7,2,\"Latent\",0,0,0,\"Use same checkpoint\",\"Use same sampler\",\"\",\"\",[],\"None\",false,\"\",0.8,-1,false,-1,0,0,0,\"from modules.processing import process_images\\n\\np.width = 768\\np.height = 768\\np.batch_size = 2\\np.steps = 10\\n\\nreturn process_images(p)\",2,false,false,\"positive\",\"comma\",0,false,false,\"\",\"Seed\",\"\",[],\"Nothing\",\"\",[],\"Nothing\",\"\",[],true,false,false,false,0,false,[{\"name\":\"/output/txt2img/2023-10-17/00013-377404987.png\",\"data\":\"http://localhost:7860/file=/output/txt2img/2023-10-17/00013-377404987.png\",\"is_file\":true}],\"{\\\"prompt\\\": \\\"a red star dark atmosphere watched from space\\\", \\\"all_prompts\\\": [\\\"a red star dark atmosphere watched from space\\\"], \\\"negative_prompt\\\": \\\"\\\", \\\"all_negative_prompts\\\": [\\\"\\\"], \\\"seed\\\": 377404987, \\\"all_seeds\\\": [377404987], \\\"subseed\\\": 2520187901, \\\"all_subseeds\\\": [2520187901], \\\"subseed_strength\\\": 0, \\\"width\\\": 512, \\\"height\\\": 512, \\\"sampler_name\\\": \\\"DPM++ 2M Karras\\\", \\\"cfg_scale\\\": 7, \\\"steps\\\": 20, \\\"batch_size\\\": 1, \\\"restore_faces\\\": false, \\\"face_restoration_model\\\": null, \\\"sd_model_name\\\": \\\"sd-v1-5-inpainting\\\", \\\"sd_model_hash\\\": \\\"c6bbc15e32\\\", \\\"sd_vae_name\\\": null, \\\"sd_vae_hash\\\": null, \\\"seed_resize_from_w\\\": -1, \\\"seed_resize_from_h\\\": -1, \\\"denoising_strength\\\": null, \\\"extra_generation_params\\\": {}, \\\"index_of_first_image\\\": 0, \\\"infotexts\\\": [\\\"a red star dark atmosphere watched from space\\\\nSteps: 20, Sampler: DPM++ 2M Karras, CFG scale: 7, Seed: 377404987, Size: 512x512, Model hash: c6bbc15e32, Model: sd-v1-5-inpainting, Conditional mask weight: 1.0, Version: v1.6.0\\\"], \\\"styles\\\": [], \\\"job_timestamp\\\": \\\"20231017202706\\\", \\\"clip_skip\\\": 1, \\\"is_using_inpainting_conditioning\\\": true}\",\"<p>a red star dark atmosphere watched from space<br>\\nSteps: 20, Sampler: DPM++ 2M Karras, CFG scale: 7, Seed: 377404987, Size: 512x512, Model hash: c6bbc15e32, Model: sd-v1-5-inpainting, Conditional mask weight: 1.0, Version: v1.6.0</p>\",\"<p class='comments'></p><div class='performance'><p class='time'>Time taken: <wbr><span class='measurement'>4.5 sec.</span></p><p class='vram'><abbr title='Active: peak amount of video memory used during generation (excluding cached data)'>A</abbr>: <span class='measurement'>1.79 GB</span>, <wbr><abbr title='Reserved: total amout of video memory allocated by the Torch library '>R</abbr>: <span class='measurement'>2.42 GB</span>, <wbr><abbr title='System: peak amout of video memory allocated by all running programs, out of total capacity'>Sys</abbr>: <span class='measurement'>3.5/6 GB</span> (57.8%)</p></div>\"],\"event_data\":null,\"fn_index\":96,\"session_hash\":\"jn0alhfxvz\"}";
                    sendBuffer = new ArraySegment<byte>(Encoding.UTF8.GetBytes(sendData));
                    await ws.SendAsync(sendBuffer, WebSocketMessageType.Text, true, CancellationToken.None);

                    result = await ws.ReceiveAsync(new ArraySegment<byte>(buffer), CancellationToken.None);
                    message = Encoding.UTF8.GetString(buffer, 0, result.Count);

                    result = await ws.ReceiveAsync(new ArraySegment<byte>(buffer), CancellationToken.None);
                    message = Encoding.UTF8.GetString(buffer, 0, result.Count);

                    await ws.CloseAsync(WebSocketCloseStatus.NormalClosure, "", CancellationToken.None);

                    end = message;
                }
            });
            await receiveTask;

            return GetDownloadURL(end);
        }

        //cuts and creates the download link for the generated file
        public static string GetDownloadURL(string result)
        {
            var beginIndex = result.IndexOf("/out");
            var subStr = result.Substring(beginIndex, result.Length - beginIndex);
            var endIndex = subStr.IndexOf("png") + 3;
            var fileLocation = subStr.Substring(0, endIndex);
            
            return StableDiffusionHost + fileLocation;
        }
    }
}
