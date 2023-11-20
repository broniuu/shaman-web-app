import {Component, inject} from '@angular/core';
import {ToastService} from "../../services/toast/toast.service";

@Component({
  selector: 'app-toasts',
  templateUrl: './toasts-container.component.html',
  styleUrls: ['./toasts-container.component.css'],
  host: { class: 'toast-container position-fixed top-0 end-0 p-3', style: 'z-index: 1200' }
})
export class ToastsContainerComponent {
  toastService = inject(ToastService);
}
