import axios from 'axios';

export default {

  getPhotos() {
    return axios.get('/post');
  },

  addComment(commentObj) {
      return axios.post('/comments/comment/create', commentObj);
  },

  addLike(newImage) {
      return axios.put(`/posts/${newImage.id}`, newImage);
  },

  unLike() {
      this.images.likes--;
      return axios.put(`/posts/${this.images.id}`, this.images.likes);
  }

  

}