import React, { useState, useRef, useEffect } from 'react';
import {
  View,
  Text,
  TextInput,
  TouchableOpacity,
  StyleSheet,
  Dimensions,
  KeyboardAvoidingView,
  Platform,
  Animated,
} from 'react-native';
import { LinearGradient } from 'expo-linear-gradient';
import { router } from 'expo-router';
import CustomButton from '../components/customButton';
import { Ionicons } from '@expo/vector-icons';

const { width, height } = Dimensions.get('window');

export default function LoginScreen() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [showPassword, setShowPassword] = useState(false);

  // Animations
  const fadeAnim = useRef(new Animated.Value(0)).current;
  const blob1Anim = useRef(new Animated.Value(0)).current;
  const blob2Anim = useRef(new Animated.Value(0)).current;
  const blobScale = useRef(new Animated.Value(0)).current;

  useEffect(() => {
    // Fade-in
    Animated.timing(fadeAnim, {
      toValue: 1,
      duration: 1200,
      useNativeDriver: true,
    }).start();

    // Smooth oscillations
    const loopAnim = (anim: Animated.Value, duration: number) => {
      Animated.loop(
        Animated.sequence([
          Animated.timing(anim, { toValue: 1, duration, useNativeDriver: true }),
          Animated.timing(anim, { toValue: 0, duration, useNativeDriver: true }),
        ])
      ).start();
    };

    loopAnim(blob1Anim, 5000);
    loopAnim(blob2Anim, 4000);

    // Scale pulse
    Animated.loop(
      Animated.sequence([
        Animated.timing(blobScale, { toValue: 1, duration: 3000, useNativeDriver: true }),
        Animated.timing(blobScale, { toValue: 0, duration: 3000, useNativeDriver: true }),
      ])
    ).start();
  }, []);

  // Interpolations
  const blob1Y = blob1Anim.interpolate({ inputRange: [0, 1], outputRange: [-15, 15] });
  const blob2Y = blob2Anim.interpolate({ inputRange: [0, 1], outputRange: [-20, 20] });
  const blobScaleVal = blobScale.interpolate({ inputRange: [0, 1], outputRange: [0.9, 1.1] });

  return (
    <LinearGradient
      colors={['#ffffff', '#f2f2f2', '#ff6b35']}
      start={{ x: 0, y: 0 }}
      end={{ x: 1, y: 1 }}
      style={styles.container}
    >
      {/* Animated blobs */}
      <Animated.View
        style={[styles.blob1, { transform: [{ translateY: blob1Y }, { scale: blobScaleVal }] }]}
      />
      <Animated.View style={[styles.blob2, { transform: [{ translateY: blob2Y }] }]} />

      <KeyboardAvoidingView
        behavior={Platform.OS === 'ios' ? 'padding' : 'height'}
        style={styles.inner}
      >
        <Animated.View style={{ opacity: fadeAnim, alignItems: 'center', width: '100%' }}>
          {/* Welcome title */}
          <Text style={styles.title}>Welcome!</Text>
          <Text style={styles.subtitle}>Sign in to continue</Text>

          {/* Email input */}
          <TextInput
            style={styles.input}
            placeholder="Email Address"
            placeholderTextColor="#aaa"
            value={email}
            onChangeText={setEmail}
            keyboardType="email-address"
          />

          {/* Password input with eye icon */}
          <View style={styles.passwordContainer}>
            <TextInput
              style={styles.passwordInput}
              placeholder="Password"
              placeholderTextColor="#aaa"
              value={password}
              onChangeText={setPassword}
              secureTextEntry={!showPassword}
            />
            <TouchableOpacity onPress={() => setShowPassword(!showPassword)}>
              <Ionicons name={showPassword ? 'eye-off' : 'eye'} size={22} color="#aaa" />
            </TouchableOpacity>
          </View>

          {/* Forgot password link */}
          <TouchableOpacity style={styles.forgotPassword} onPress={() => router.replace('/welcome')}>
            <Text style={styles.linkText}>Forgot Password?</Text>
          </TouchableOpacity>

          {/* Login button */}
          <CustomButton title="Login" onPress={() => router.replace('/welcome')} />

          {/* Signup link below button */}
          <View style={styles.signupContainer}>
            <Text style={styles.signupText}>New User?</Text>
            <TouchableOpacity onPress={() => router.replace('/welcome')}>
              <Text style={styles.signupLink}>Signup</Text>
            </TouchableOpacity>
          </View>
        </Animated.View>
      </KeyboardAvoidingView>
    </LinearGradient>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1 },
  inner: { flex: 1, justifyContent: 'center', alignItems: 'center', paddingHorizontal: 20 },
  blob1: {
    position: 'absolute',
    width: 220,
    height: 220,
    borderRadius: 110,
    backgroundColor: '#ff6b35',
    top: -50,
    left: -70,
    opacity: 0.25,
  },
  blob2: {
    position: 'absolute',
    width: 160,
    height: 160,
    borderRadius: 80,
    backgroundColor: '#f2f2f2',
    bottom: -30,
    right: -50,
    opacity: 0.3,
  },
  title: {
    color: '#1a1a1a',
    fontSize: width > 400 ? 38 : 32,
    fontWeight: '900',
    marginBottom: 10,
    textShadowColor: '#ff6b35',
    textShadowOffset: { width: 0, height: 2 },
    textShadowRadius: 4,
  },
  subtitle: { color: '#333', fontSize: width > 400 ? 18 : 16, marginBottom: 30, opacity: 0.9 },
  input: {
    backgroundColor: '#fff',
    color: '#1a1a1a',
    width: '90%',
    padding: 16,
    marginBottom: 15,
    borderRadius: 12,
    borderWidth: 2,
    borderColor: '#ff6b35',
    fontSize: 16,
  },
  passwordContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: '#fff',
    borderRadius: 12,
    borderWidth: 2,
    borderColor: '#ff6b35',
    width: '90%',
    paddingHorizontal: 14,
    marginBottom: 8,
  },
  passwordInput: { flex: 1, color: '#1a1a1a', paddingVertical: 14, fontSize: 16 },
  forgotPassword: { width: '90%', alignItems: 'flex-end', marginBottom: 25 },
  linkText: { color: '#ff6b35', fontSize: 14, fontWeight: '600' },
  signupContainer: { flexDirection: 'row', marginTop: 20 },
  signupText: { color: '#1a1a1a', fontSize: 15, marginRight: 5 },
  signupLink: { color: '#ff6b35', fontSize: 15, fontWeight: '700' },
});