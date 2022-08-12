import axios from 'axios';

export default {

  getPhotos() {
    return axios.get('/post');
  },

  addComment(commentObj) {
      return axios.post(`/post/${commentObj.postId}/create`, null, {params: {comment:commentObj.comment}});
  },

  addLike(newImage) {
      return axios.put(`/posts/${newImage.id}`, newImage);
  },

  unLike() {
      this.images.likes--;
      return axios.put(`/posts/${this.images.id}`, this.images.likes);
  },

  getUser(userId) {
    alert('inside getUser');
    return axios.get(`/user/${userId}`);
  },

  addPicture(caption, picture) {
    const formData = new FormData();
    formData.append('desc', caption);
    formData.append('mpf', picture);
    return axios.post(`/post/create`, formData, {headers: {'Content-Type': 'multipart/form-data'}});
  }//will return fields in an object - post_id, user_id, pictureLink, description ,timestamp
}