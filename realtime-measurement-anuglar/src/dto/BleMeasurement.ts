export class BleMeasurement {
    reporter:     Reporter | undefined;
    measurements: Measurement[] | undefined;
}

export class Measurement {
    type:          string | undefined;
    deviceAddress: string | undefined;
    rssi:          number | undefined;
    data:          Data | undefined;
}

export class Data {
    temperature: number | undefined;
    humidity:    number | undefined;
}

export class Reporter {
    name:            string | undefined;
    identifier:      string | undefined;
    ipv4:            string | undefined;
    ipv6:            string | undefined;
    hwType:          string | undefined;
    firmwareVersion: string | undefined;
}