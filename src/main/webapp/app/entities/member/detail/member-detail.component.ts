import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IMember } from '../member.model';

@Component({
  standalone: true,
  selector: 'jhi-member-detail',
  templateUrl: './member-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class MemberDetailComponent {
  member = input<IMember | null>(null);

  previousState(): void {
    window.history.back();
  }
}
