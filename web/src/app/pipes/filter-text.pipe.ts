import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'filterText'
})
export class FilterTextPipe implements PipeTransform {

  transform(value: any[], filterText: any): any {
    if (!value) {
      return [];
    }
    if (!filterText) {
      return value;
    }
    filterText = filterText.toLowerCase();
    return value.filter(val => {
      return val.name.toLowerCase().includes(filterText);
    });
  }
}
