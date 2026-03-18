import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AttendanceSelection } from '../models/guest.model';

@Injectable({ providedIn: 'root' })
export class GuestService {
  private readonly httpClient = inject(HttpClient);

  public addMemberToGroup(groupId: number, name: string): Observable<void> {
    return this.httpClient.post<void>(`/guests`, { groupId, name });
  }

  public deleteOne(guestId: number): Observable<void> {
    return this.httpClient.delete<void>(`/guests/${guestId}`);
  }
}
