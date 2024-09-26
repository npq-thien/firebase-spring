import { useEffect, useState } from "react";
import axios from "axios";
import { api } from "../config/AxiosConfig";

const ImageUploader = () => {
  const [images, setImages] = useState<File[]>([]);
  const [imagePreviewUrl, setImagePreviewUrl] = useState<string[]>([]);
  const [taskId, setTaskId] = useState<Number>();

  useEffect(() => {
    // Create object URLs for each image file
    if (images.length > 0) {
      const previewUrls = images.map((image) => URL.createObjectURL(image));
      setImagePreviewUrl(previewUrls);

      // Clean up object URLs on component unmount or image change
      return () => {
        previewUrls.forEach((url) => URL.revokeObjectURL(url));
      };
    }
  }, [images]);

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files) {
      setImages(Array.from(e.target.files));
    }
  };

  const handleSubmit = async () => {
    const formData = new FormData();
    images.forEach((image) => {
      formData.append("files", image);
    });

    try {
      const response = await api.post(
        `/api/image/upload/${taskId}`,
        formData,
        {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        }
      );
      console.log("Images uploaded successfully:", response.data);
    } catch (error) {
      console.error("Error uploading images", error);
    }
  };

  console.log(taskId)

  return (
    <div className="flex flex-col gap-4">
      <input multiple type="file" onChange={handleFileChange} />
      <input
        type="text"
        className="border-2 border-black rounded-md w-fit px-2"
        placeholder="Input task id"
        onChange={(e) => setTaskId(Number(e.target.value))}
      />

      {images && (
        <div className="flex gap-2">
          {imagePreviewUrl.map((img, index) => (
            <div key={index}>
              <img
                className="w-auto h-[100px] rounded-md shadow-md"
                src={img}
                alt={img}
              />
              <p>{images[index].name}</p>
            </div>
          ))}
        </div>
      )}

      <button
        onClick={handleSubmit}
        className="rounded-md bg-blue-400 p-2 w-fit"
      >
        Send to Firebase
      </button>
    </div>
  );
};

export default ImageUploader;
