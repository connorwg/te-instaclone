import axios from 'axios';

export default {

  getPhotos() {
    return axios.get('/post');
  },

  addComment(commentObj) {
      return axios.post('/comments/comment/create', commentObj);
  },

  addLike() {
      this.images.likes++;
      return axios.put(`/posts/${this.images.id}`, this.images.likes);
  },

  unLike() {
      this.images.likes--;
      return axios.put(`/posts/${this.images.id}`, this.images.likes);
  }

  

}