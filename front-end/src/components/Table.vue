<template>


  <v-card>
    <v-card-title>
      Nutrition
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="desserts"
      :search="search"
    ></v-data-table>
  </v-card>
<!-- <div>
    {{knowledge.results.bindings[0].subject.value}}
    <hr>
    {{knowledge.results.bindings[0].predicate.value}}
    <hr>
    {{knowledge.results.bindings[0].object.value}}

</div> -->
  <!-- <div class="table">
    <div class="container mb-3 mt-3">
      <table
        class="table table-striped table-bordered mydatatable"
        style="width: 100%"
      >
        <thead>
          <tr>
            <th>Subject</th>
            <th>Predicate</th>
            <th>Object</th>
          </tr>
        </thead>

        <tbody>
          <tr v-for="values in this.knowledge" v-bind:key="values.id">
              <td></td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>-->
</template>

<script>
import axios from "axios";

export default {
  name: "knowledge",
  data() {
    return {
      knowledge: [],
      columns: ["Subject", "Predicate", "Object"],
    };
  },
  created: async function () {
    let response =  await axios.get(`http://localhost:3000/knowledge`);
    console.log(response.data);
    let knowledge = response.data;
    this.knowledge =  knowledge;
  },
};

</script>
