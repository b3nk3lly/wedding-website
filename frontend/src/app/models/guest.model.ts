export enum AttendanceSelection {
    YES = 'YES',
    MAYBE = 'MAYBE',
    NO = 'NO'
}

export interface Guest {
    name: string;
    isAttending?: AttendanceSelection;
    selectedMealId?: number;
    hasAllergies?: boolean;
    allergies?: string;
}

export interface Group {
    name: string;
    members: Guest[]
}