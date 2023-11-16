import {Component, EventEmitter, Input, Output} from '@angular/core';
import {SharedService} from "../../services/shared.service";

@Component({
  selector: 'app-search-bar',
  templateUrl: './search-bar.component.html',
  styleUrls: ['./search-bar.component.css']
})
export class SearchBarComponent {
  @Output() childEvent = new EventEmitter<any>();
  @Input() searchTerm :string;
  constructor(private sharedService: SharedService) {}

  sendDataToParent(data: any) {
    this.childEvent.emit(data);
  }
  onSearch() {
this.changeData(this.searchTerm);
  }
  changeData(newData: any) {
    this.sharedService.updateData(newData);
  }
}
