import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface GuestResponseDto {
  name: string;
  isAttending?: boolean;
  selectedMealId?: string;
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
  constructor(private httpClient: HttpClient) {}

  public getAllGroups(): Observable<GroupResponseDto[]> {
    return this.httpClient.get<GroupResponseDto[]>(`/groups`);
  }

  public createGroup(creationDto: GroupCreationDto) {
    return this.httpClient.post<GroupResponseDto>(`/groups`, creationDto);
  }
}
