<template>
  <div id="main-grid">
    <h1 id="postpic-header">
      <Header></Header>
    </h1>
    <div class="container py-5">
      <div class="row py-4">
        <div class="col-lg-6 mx-auto">
          <!-- Upload image input-->
          <div
            class="input-group mb-3 px-2 py-2 rounded-pill bg-white shadow-lg"
          >
            

            <!-- <label
              id="upload-label"
              for="upload"
              class="font-weight-light text-muted"
              >Choose file</label
            > -->
            <div class="input-group-append">
              <label for="upload" >
                <!-- <input
                  id="upload"
                  type="file"
                  onchange="readURL(this);"
                  class="form-control border-0"
                /> -->
                <i class="fa fa-cloud-upload mr-2 text-muted"></i>
                  <input class="btn btn-light m-0 rounded-pill px-4" 
                    id="upload"
                    ref="imagefile"
                    type="file"
                    accept="image/*"
                    @change="preview"
                  />
                
              </label>
            </div>
          </div>
          <p class="font-italic text-white text-center">
            The image uploaded will be rendered inside the box below.
          </p>
          <div class="image-area mt-4">
          
            <div class="preview-div"  v-if="item.imageUrl">
              <img id="preview"  :src="item.imageUrl" />
              <input
              class="btn btn-light m-0 rounded-pill px-4"
                type="text"
                id="caption"
                v-model="item.caption"
                placeholder="Add a Caption!"
              />
              
            </div>
          </div>
          <button
                id="postpicture"
                class="badge bg-info"
                v-on:click.prevent="postPicture"
              >
                Post
              </button>
              <!--to .prevent default function of click-->
              <button
                id="cancelpost"
                class="badge bg-secondary"
                v-on:click.prevent="reset"
              >
                Cancel
              </button>
              <div id="progressbar">
                <label
                  class="active progress-bar bg-info progress-bar-striped"
                  style="width: 40%"
                  v-if="!gotResponse && postRequestSent"
                  >Uploading... Please wait...</label
                >
                <label
                  style="color: blue; font-weight: bold"
                  v-else-if="gotResponse"
                  >Picture uploaded!</label
                >
              </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Header from "./Header.vue";
import photoService from "../services/PhotoService.js";

export default {
  name: "postpic",
  components: {
    Header,
  },
  data() {
    return {
      item: {
        //...
        image: null,
        imageUrl: null,
        caption: null,
      },
      gotResponse: false,
      postRequestSent: false,
    };
  },
  methods: {
    preview(event) {
      const file = event.target.files[0]; //to retrieve data from event
      this.item.image = file;
      this.item.imageUrl = URL.createObjectURL(file);
    },
    postPicture() {
      this.postRequestSent = true;
      photoService
        .addPicture(this.item.caption, this.item.image)
        .then((response) => {
          this.gotResponse = true;
          if (response.status === 200) {
            const data = response.data;
            this.$store.commit("ADD_PHOTOS", data);
          }
        })
        .catch((error) => {
          alert(error.response.status);
          alert("Message upload failed");
        });
    },
    reset() {
      (this.item.image = null),
        (this.item.imageUrl = null),
        (this.item.caption = null),
        (this.gotResponse = false),
        (this.postRequestSent = false);
    },
  },
};
</script>
<style>



#progressbar {
  display: flex;
  justify-content: center;
}
.preview-div  {
  display: flex;
  flex-direction: column;
}

#postpicture {
display: flex;
justify-content: center;
margin-left: 40%;
margin-top: 2%;
margin-bottom: 2%;
width: 20%;
box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
}

#cancelpost {
  display: flex;
  justify-content: center;
  margin-left: 40%;
  margin-bottom: 2%;
  width: 20%;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
}

#caption {
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
  
}

/* input[type="file"] {
  display: none;
} */
#upload-label {
  position: absolute;
  top: 50%;
  left: 1rem;
  transform: translateY(-50%);
}

.image-area {
  border: 2px dashed rgba(255, 255, 255, 0.7);
  padding: 1rem;
  position: relative;
}

.image-area::before {
  content: "Uploaded image result";
  color: #fff;
  font-weight: bold;
  text-transform: uppercase;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-size: 0.8rem;
  z-index: 1;
}

.image-area img {
  z-index: 2;
  position: relative;
}
#main-grid {
  height: 100vh;
  display: grid;
  grid-template-columns: 1fr;
  grid-template-areas:
    "postpic-header"
    "upload"
    "preview";
  grid-template-rows: 1fr 6fr 1fr;
}
#preview {
  display: grid;
  grid-area: preview;
  justify-items: center;
  margin-bottom: 5%;
}
#postpic-header {
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
  grid-area: postpic-header;
  font-family: "Billabong";
  background-color: aliceblue;
  width: 100%;
  margin-top: 0%;
}

#upload {
  
  border-radius: 60%;
}
</style>

<!--
<div class="input-group mb-3 px-2 py-2 rounded-pill bg-white shadow-sm">
      <input
        id="upload"
        ref="imagefile"
        type="file"
        accept="image/*"
        @change="preview"
      />
      <div v-if="item.imageUrl">
        <img id="preview" :src="item.imageUrl" />
        <input
          type="text"
          id="caption"
          v-model="item.caption"
          placeholder="Add a Caption!"
        />
        <button
          id="postpicture"
          class="badge bg-info"
          v-on:click.prevent="postPicture"
        >
          Post</button
        ><--to .prevent default function of click--><!--
        <button
          id="cancelpost"
          class="badge bg-secondary"
          v-on:click.prevent="reset"
        >
          Cancel
        </button>
        <div>
          <label
            class="progress-bar bg-info progress-bar-striped"
            style="width: 40%"
            v-if="!gotResponse && postRequestSent"
            >Uploading... Please wait...</label
          >
          <label style="color: blue; font-weight: bold" v-else-if="gotResponse"
            >Picture uploaded!</label
          >
        </div>
      </div>
    </div>
    -->