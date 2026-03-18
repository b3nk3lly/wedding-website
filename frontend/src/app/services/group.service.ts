import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AttendanceSelection } from '../models/guest.model';

export interface GuestResponseDto {
  id: number;
  name: string;
  isAttending?: AttendanceSelection;
  selectedMealId?: string;
  hasAllergies?: boolean;
  allergies?: string;
}

export interface UserResponseDto {
  username: string;
}

export interface GroupResponseDto {
  id: number;
  name: string;
  user: UserResponseDto;
  members: GuestResponseDto[];
}

export interface GroupCreationDto {
  name: string;
  username: string;
  password: string;
}

@Injectable({ providedIn: 'root' })
export class GroupService {
  private readonly httpClient = inject(HttpClient);

  public getAllGroups(): Observable<GroupResponseDto[]> {
    return this.httpClient.get<GroupResponseDto[]>(`/groups`);
  }

  public createGroup(creationDto: GroupCreationDto) {
    return this.httpClient.post<GroupResponseDto>(`/groups`, creationDto);
  }

  public getCurrentUserGroup() {
    return this.httpClient.get<GroupResponseDto>(`/groups/mine`);
  }
}
