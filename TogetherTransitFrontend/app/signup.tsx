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
    Alert,
    ActivityIndicator,
} from 'react-native';
import { LinearGradient } from 'expo-linear-gradient';
import { router } from 'expo-router';
import CustomButton from '../components/customButton';
import { Ionicons } from '@expo/vector-icons';
import { useSearchParams } from 'expo-router/build/hooks';
import { signupDriver, signupParent } from '../services/api';

const { width } = Dimensions.get('window');

export default function SignupScreen() {
    // State variables
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [licenseNumber, setLicenseNumber] = useState('');
    const [showPassword, setShowPassword] = useState(false);
    const [showConfirmPassword, setShowConfirmPassword] = useState(false);
    const [isLoading, setIsLoading] = useState(false);

    // Get role from query params
    const role = useSearchParams().get('role');
    const isdriver = role === 'driver';

    // Animation setup
    const fadeAnim = useRef(new Animated.Value(0)).current;
    const blob1Anim = useRef(new Animated.Value(0)).current;
    const blob2Anim = useRef(new Animated.Value(0)).current;
    const blobScale = useRef(new Animated.Value(0)).current;

    useEffect(() => {
        // Fade-in animation
        Animated.timing(fadeAnim, {
            toValue: 1,
            duration: 1200,
            useNativeDriver: true,
        }).start();

        // Helper function for looping animations
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

    // Validation logic
    const handleSignup = async () => {
        // Check required fields
        if (!firstName || !lastName || !email || !password || !confirmPassword) {
            Alert.alert('Error', 'All fields are required.');
            return;
        }

        // If driver, check license number
        if (isdriver) {
            if (!licenseNumber) {
                Alert.alert('Error', 'License number is required for drivers.');
                return;
            }
            // Validate format: 8-15 characters, digits and spaces only
            if (!/^[0-9\s]{8,15}$/.test(licenseNumber)) {
                Alert.alert('Error', 'License number must be 8-15 characters long and contain only digits and spaces.');
                return;
            }
        }

        // Validate email format
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(email)) {
            Alert.alert('Error', 'Please enter a valid email address.');
            return;
        }

        // Password length check
        if (password.length < 6) {
            Alert.alert('Error', 'Password must be at least 6 characters long.');
            return;
        }

        // Confirm password match
        if (password !== confirmPassword) {
            Alert.alert('Error', 'Passwords do not match.');
            return;
        }

        // Send to backend
        setIsLoading(true);
        try {
            if (isdriver) {
                // Call driver signup API
                const response = await signupDriver(firstName, lastName, email, password, licenseNumber);
                
                // Store user data if needed (can use AsyncStorage)
                Alert.alert('Success', 'Account created successfully!');
                // Navigate to driver home screen
                router.replace('/driverHomeScreen');
            } else {
                // Call parent signup API
                const response = await signupParent(firstName, lastName, email, password);
                
                // Store user data if needed
                Alert.alert('Success', 'Account created successfully!');
                // Navigate to parent home screen
                router.replace('/parentHomeScreen');
            }
        } catch (error: any) {
            console.error('Signup error:', error);
            const errorMessage = error.response?.data?.message || error.message || 'Signup failed. Please try again.';
            Alert.alert('Error', errorMessage);
        } finally {
            setIsLoading(false);
        }
    };

    // UI Layout
    return (
        <LinearGradient
            colors={['#ffffff', '#f2f2f2', '#ff6b35']}
            start={{ x: 0, y: 0 }}
            end={{ x: 1, y: 1 }}
            style={styles.container}
        >
            <Animated.View style={[styles.blob1, { transform: [{ translateY: blob1Y }, { scale: blobScaleVal }] }]} />
            <Animated.View style={[styles.blob2, { transform: [{ translateY: blob2Y }] }]} />

            <KeyboardAvoidingView
                behavior={Platform.OS === 'ios' ? 'padding' : 'height'}
                keyboardVerticalOffset={Platform.OS === 'ios' ? 60 : 0}
                style={styles.inner}
            >
                <Animated.View style={{ opacity: fadeAnim, alignItems: 'center', width: '100%' }}>
                    <Text style={styles.title}>Create Account</Text>
                    <Text style={styles.subtitle}>Sign up to get started</Text>

                    <View style={styles.inputGroup}>
                        <TextInput
                            style={styles.input}
                            placeholder="First Name"
                            placeholderTextColor="#aaa"
                            value={firstName}
                            onChangeText={setFirstName}
                            editable={!isLoading}
                        />
                    </View>
                    <View style={styles.inputGroup}>
                        <TextInput
                            style={styles.input}
                            placeholder="Last Name"
                            placeholderTextColor="#aaa"
                            value={lastName}
                            onChangeText={setLastName}
                            editable={!isLoading}
                        />
                    </View>
                    <View style={styles.inputGroup}>
                        <TextInput
                            style={styles.input}
                            placeholder="Email Address"
                            placeholderTextColor="#aaa"
                            value={email}
                            onChangeText={setEmail}
                            keyboardType="email-address"
                            editable={!isLoading}
                        />
                    </View>

                    <View style={styles.inputGroup}>
                        <View style={styles.passwordContainer}>
                            <TextInput
                                style={styles.passwordInput}
                                placeholder="Password"
                                placeholderTextColor="#aaa"
                                value={password}
                                onChangeText={setPassword}
                                secureTextEntry={!showPassword}
                                editable={!isLoading}
                            />
                            <TouchableOpacity onPress={() => setShowPassword(!showPassword)} disabled={isLoading}>
                                <Ionicons name={showPassword ? 'eye-off' : 'eye'} size={22} color="#aaa" />
                            </TouchableOpacity>
                        </View>
                    </View>

                    <View style={styles.inputGroup}>
                        <View style={styles.passwordContainer}>
                            <TextInput
                                style={styles.passwordInput}
                                placeholder="Confirm Password"
                                placeholderTextColor="#aaa"
                                value={confirmPassword}
                                onChangeText={setConfirmPassword}
                                secureTextEntry={!showConfirmPassword}
                                editable={!isLoading}
                            />
                            <TouchableOpacity onPress={() => setShowConfirmPassword(!showConfirmPassword)} disabled={isLoading}>
                                <Ionicons name={showConfirmPassword ? 'eye-off' : 'eye'} size={22} color="#aaa" />
                            </TouchableOpacity>
                        </View>
                    </View>

                    {isdriver && (
                        <View style={styles.inputGroup}>
                            <TextInput
                                style={styles.input}
                                value={licenseNumber}
                                onChangeText={setLicenseNumber}
                                placeholder="License Number (8-15 digits/spaces)"
                                placeholderTextColor="#aaa"
                                autoCapitalize="none"
                                keyboardType="default"
                                editable={!isLoading}
                            />
                        </View>
                    )}

                    {isLoading ? (
                        <ActivityIndicator size="large" color="#ff6b35" style={{ marginTop: 20 }} />
                    ) : (
                        <CustomButton title="Signup" onPress={handleSignup} />
                    )}

                    <View style={styles.signupContainer}>
                        <Text style={styles.signupText}>Already have an account?</Text>
                        <TouchableOpacity onPress={() => router.replace('/Login')} disabled={isLoading}>
                            <Text style={styles.signupLink}>Login</Text>
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
    blob1: { position: 'absolute', width: 220, height: 220, borderRadius: 110, backgroundColor: '#ff6b35', top: -50, left: -70, opacity: 0.25 },
    blob2: { position: 'absolute', width: 160, height: 160, borderRadius: 80, backgroundColor: '#f2f2f2', bottom: -30, right: -50, opacity: 0.3 },
    title: { color: '#1a1a1a', fontSize: width > 400 ? 34 : 28, fontWeight: '900', marginBottom: 10, textShadowColor: '#ff6b35', textShadowOffset: { width: 0, height: 2 }, textShadowRadius: 4 },
    subtitle: { color: '#333', fontSize: width > 400 ? 18 : 16, marginBottom: 18, opacity: 0.9 },
    input: { backgroundColor: '#fff', color: '#1a1a1a', width: '100%', padding: 14, borderRadius: 12, borderWidth: 2, borderColor: '#ff6b35', fontSize: 16 },
    passwordContainer: { flexDirection: 'row', alignItems: 'center', backgroundColor: '#fff', borderRadius: 12, borderWidth: 2, borderColor: '#ff6b35', width: '100%', paddingHorizontal: 14 },
    passwordInput: { flex: 1, color: '#1a1a1a', paddingVertical: 12, fontSize: 16 },
    inputGroup: { width: width * 0.9, marginBottom: 10 },
    signupContainer: { flexDirection: 'row', marginTop: 15 },
    signupText: { color: '#333', fontSize: 16, marginRight: 5 },
    signupLink: { color: '#ff6b35', fontSize: 16, fontWeight: '600' },
});