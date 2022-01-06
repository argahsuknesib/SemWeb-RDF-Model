<template>
  <table
    id="example"
    class="table table-striped table-bordered"
    style="width: 100%"
  >
    <thead>
      <tr>
        <th v-for="key in columns" v-bind:key="key">{{ key }}</th>
      </tr>
    </thead>

    <tbody>
      <tr v-for="random in knowledge" v-bind:key="random">
        <td>{{ random.subject.value }}</td>
        <!-- <td>{{ random.subject.type }}</td> -->
        <td>{{ random.predicate.value }}</td>
        <!-- <td>{{ random.predicate.type }}</td> -->
        <td>{{ random.object.value }}</td>
        <!-- <td>{{ random.object.type }}</td> -->

      </tr>
    </tbody>

    <tfoot>
      <tr>
        <th v-for="key in columns" v-bind:key="key">{{ key }}</th>
      </tr>
    </tfoot>
  </table>
</template>

<script>
import axios from "axios";

export default {
  name: "knowledge",
  data() {
    return {
      knowledge: [],
      columns: ["Subject","Predicate","Object"],
    };
  },
  created: async function () {
    let response = await axios.get(`http://localhost:3000/knowledge`);
    console.log(response.data);
    let knowledge = response.data.results.bindings;
    this.knowledge = knowledge;
  },
};
</script>
