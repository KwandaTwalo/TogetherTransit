// 1. Import React and hooks
import React, { useEffect, useState } from 'react';

// 2. Import React Native UI components
import { View, Text, TextInput, TouchableOpacity, ScrollView, StyleSheet } from 'react-native';

// 3. Import axios instance (configured with backend base URL)
import api from '../../services/api'; // adjust path if needed

// 4. Define the main screen component
//  IMPORTANT: must be exported as "default" so Expo Router can use it
export default function AddressScreen() {
  // 5. State to hold list of addresses from backend
  const [addresses, setAddresses] = useState<any[]>([]);

  // 6. State to hold form input values
  const [form, setForm] = useState({
    streetNumber: '',
    streetName: '',
    suburb: '',
    city: '',
    postalCode: ''
  });

  // 7. useEffect runs once when screen loads
  useEffect(() => {
    fetchAddresses(); // load all addresses from backend
  }, []);

  // 8. Function: fetch all addresses (GET request)
  const fetchAddresses = () => {
    api.get("/address/getAllAddresses")
      .then(res => setAddresses(res.data)) // update state with backend data
      .catch(err => console.error(err));    // log error if request fails
  };

  // 9. Function: create new address (POST request)
  const createAddress = () => {
    api.post("/address/create", form)
      .then(() => {
        // reset form after success
        setForm({ streetNumber: '', streetName: '', suburb: '', city: '', postalCode: '' });
        fetchAddresses(); // reload list
      })
      .catch(err => console.error(err));
  };

  // 10. Function: update existing address (PUT request)
  const updateAddress = (id: number) => {
    api.put(`/address/update/${id}`, form)
      .then(() => fetchAddresses()) // reload list after update
      .catch(err => console.error(err));
  };

  // 11. Function: delete address (DELETE request)
  const deleteAddress = (id: number) => {
    api.delete(`/address/delete/${id}`)
      .then(() => fetchAddresses()) // reload list after delete
      .catch(err => console.error(err));
  };

  // 12. UI rendering
  return (
    <ScrollView style={styles.container}>
      {/* Title */}
      <Text style={styles.title}>Manage Addresses</Text>

      {/* Form inputs */}
      <View style={styles.form}>
        <TextInput
          style={styles.input}
          placeholder="Street Number"
          placeholderTextColor="#888"
          value={form.streetNumber}
          onChangeText={text => setForm({ ...form, streetNumber: text })}
        />
        <TextInput
          style={styles.input}
          placeholder="Street Name"
          placeholderTextColor="#888"
          value={form.streetName}
          onChangeText={text => setForm({ ...form, streetName: text })}
        />
        <TextInput
          style={styles.input}
          placeholder="Suburb"
          placeholderTextColor="#888"
          value={form.suburb}
          onChangeText={text => setForm({ ...form, suburb: text })}
        />
        <TextInput
          style={styles.input}
          placeholder="City"
          placeholderTextColor="#888"
          value={form.city}
          onChangeText={text => setForm({ ...form, city: text })}
        />
        <TextInput
          style={styles.input}
          placeholder="Postal Code"
          placeholderTextColor="#888"
          value={form.postalCode}
          onChangeText={text => setForm({ ...form, postalCode: text })}
        />

        {/* Submit button */}
        <TouchableOpacity style={styles.button} onPress={createAddress}>
          <Text style={styles.buttonText}>Add Address</Text>
        </TouchableOpacity>
      </View>

      {/* List of addresses */}
      {addresses.map(addr => (
        <View key={addr.addressId} style={styles.card}>
          <Text style={styles.cardText}>
            {addr.streetNumber} {addr.streetName}, {addr.suburb}, {addr.city} ({addr.postalCode})
          </Text>
          <View style={styles.actions}>
            {/* Edit button */}
            <TouchableOpacity onPress={() => updateAddress(addr.addressId)}>
              <Text style={styles.actionText}>Edit</Text>
            </TouchableOpacity>
            {/* Delete button */}
            <TouchableOpacity onPress={() => deleteAddress(addr.addressId)}>
              <Text style={styles.actionText}>Delete</Text>
            </TouchableOpacity>
          </View>
        </View>
      ))}
    </ScrollView>
  );
}

// 13. Styling (Uber-style: black background, white text)
const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: '#000', padding: 20 },
  title: { color: '#fff', fontSize: 22, fontWeight: 'bold', marginBottom: 20 },
  form: { marginBottom: 30 },
  input: {
    backgroundColor: '#111',
    color: '#fff',
    borderColor: '#333',
    borderWidth: 1,
    borderRadius: 8,
    padding: 10,
    marginBottom: 10
  },
  button: {
    backgroundColor: '#fff',
    padding: 12,
    borderRadius: 8,
    alignItems: 'center'
  },
  buttonText: { color: '#000', fontWeight: 'bold' },
  card: {
    backgroundColor: '#111',
    padding: 15,
    borderRadius: 8,
    marginBottom: 10
  },
  cardText: { color: '#fff', marginBottom: 10 },
  actions: { flexDirection: 'row', justifyContent: 'space-between' },
  actionText: { color: '#fff', fontWeight: 'bold' }
});