import React from 'react';
import { View, Text, Button, StyleSheet } from 'react-native';
import { router } from 'expo-router';

export default function DriverHomeScreen() {
  return (
    <View style={styles.container}>
      <Text style={styles.header}>Driver Dashboard</Text>

      {/* Next Pickup Card */}
      <View style={styles.card}>
        <Text style={styles.cardTitle}>Next Pickup</Text>
        <Text>Student: John Doe</Text>
        <Text>Location: 45 Main Street</Text>
        <Text>Time: 07:30 AM</Text>
      </View>

      {/* Route Map Placeholder */}
      <View style={styles.card}>
        <Text style={styles.cardTitle}>Today's Route</Text>
        <Text>[Map Component Here]</Text>
      </View>

      {/* Navigate to Trips */}
      <Button title="View Trips" onPress={() => router.push('/welcome')} />
    </View>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, padding: 20, backgroundColor: '#fff' },
  header: { fontSize: 24, fontWeight: 'bold', marginBottom: 20 },
  card: { padding: 15, marginBottom: 15, backgroundColor: '#f9f9f9', borderRadius: 8 },
  cardTitle: { fontSize: 18, fontWeight: '600', marginBottom: 5 }
});
